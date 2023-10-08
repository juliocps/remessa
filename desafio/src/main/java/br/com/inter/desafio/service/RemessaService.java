package br.com.inter.desafio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.inter.desafio.dto.Cliente;
import br.com.inter.desafio.dto.Cotacao;
import br.com.inter.desafio.dto.Remessa;
import br.com.inter.desafio.entity.CarteiraFisica;
import br.com.inter.desafio.entity.CarteiraJuridica;
import br.com.inter.desafio.entity.PessoaFisica;
import br.com.inter.desafio.entity.PessoaJuridica;
import br.com.inter.desafio.excecao.NegocioException;
import br.com.inter.desafio.repository.CarteiraFisicaRepository;
import br.com.inter.desafio.repository.CarteiraJuridicaRepository;
import br.com.inter.desafio.repository.PessoaFisicaRepository;
import br.com.inter.desafio.repository.PessoaJuridicaRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/**
 * Classe responsavel por implementar as regras de negocio da remessa 
 * @author Júlio Silva
 *
 */
@Service
public class RemessaService extends ServiceBase {

	private static final int HTTP_CODE_SUCESSO = 200;

	private static final String PESSOA_FISICA = "PF";
	
	private static final String PESSOA_JURIDICA = "PJ";

	private static final int TAMANHO_CPF = 11;

	@Autowired
	PessoaJuridicaRepository pjRepository;
	
	@Autowired
	PessoaFisicaRepository pfRepository;
	
	@Autowired
	CarteiraJuridicaRepository carteiraPjRepository;
	
	@Autowired
	CarteiraFisicaRepository carteiraPfRepository;
	
	
	/**
	 * Metodo responsavel por realizar a operacao de remessa 
	 * ( debitar de uma conta e creditar em outra conta )
	 * @param Remessa remessa
	 * @return String 
	 * @throws NegocioException 
	 * @throws ParseException 
	 */
	@Transactional(value = TxType.REQUIRED)
	public String efetuarRemessa(Remessa remessa) throws NegocioException, ParseException {
		
		Cliente depositante = enriquecerDepositante(remessa);
		Cliente beneficiario = enriquecerBeneficiario(remessa);
		
		BigDecimal cotacaoDia = obterCotacao();
		BigDecimal valorBase = new BigDecimal(remessa.getValor().replace(",", "."));
		remessa.setValorConvertido(valorBase.divide(cotacaoDia,RoundingMode.HALF_UP));
		
		
		
		
		debitar(remessa, depositante);
		creditar(remessa, beneficiario);
		
				
		return "Operação Efetuada com sucesso";
	}

	/**
	 * Metodo responsavel por buscar a cotacao do dia para remessa em dolar
	 * para realizar a remessa
	 * @param Remessa remessa
	 * @return String 
	 */
	@SuppressWarnings("deprecation")
	private BigDecimal obterCotacao() {
		BigDecimal cotacaoDecimal = new BigDecimal(0);
		
		String url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='"
				+ "05-10-2023" //TODO : mudar para a data atual
				+ "'&$top=10&$skip=0&$format=json&$select=cotacaoCompra";
		
		ResponseEntity<String> response = executarRequisicaoGet(url, null);
		
		if(response.getStatusCodeValue()==HTTP_CODE_SUCESSO ) {				
			Cotacao cotacao = gson.fromJson(response.getBody(), Cotacao.class);
			if(cotacao != null) {							
				cotacaoDecimal = new BigDecimal(cotacao.getValue().get(0).getCotacaoCompra().toString());				
			}
		}else {
			//TODO : levantar exceção
			
		}
		return cotacaoDecimal;
	}
	
	/**
	 * Metodo responsavel por enriquecer os dados do beneficiaro
	 * para realizar a remessa
	 * @param Remessa remessa
	 * @return String 
	 */
	private Cliente enriquecerBeneficiario(Remessa remessa) {
		Cliente beneficiario = new Cliente();
		PessoaFisica beneficiarioPF = new PessoaFisica();
		PessoaJuridica beneficiarioPJ = new PessoaJuridica();
		CarteiraFisica carteiraBeneficiarioPF = new CarteiraFisica();
		CarteiraJuridica carteiraBeneficiarioPJ = new CarteiraJuridica();
		
		if(remessa.getBeneficiario().length() == TAMANHO_CPF) {
			beneficiarioPF = pfRepository.buscarPessoaFisicaPorCPF(remessa.getBeneficiario());

			carteiraBeneficiarioPF.setPessoaFisica(beneficiarioPF);
			carteiraBeneficiarioPF.setMoeda("DOLAR");
			carteiraBeneficiarioPF = carteiraPfRepository.buscarCarteiraPessoaFisica(beneficiarioPF, carteiraBeneficiarioPF.getMoeda());
			
			beneficiario.setPessoaFisica(beneficiarioPF);
			beneficiario.setCarteiraPF(carteiraBeneficiarioPF);
			beneficiario.setTipoPessoa(PESSOA_FISICA);
	
		}else {
			beneficiarioPJ = pjRepository.buscarPessoaJuridicaPorCNPJ(remessa.getBeneficiario());
				
			carteiraBeneficiarioPJ.setPessoaJuridica(beneficiarioPJ);
			carteiraBeneficiarioPJ.setMoeda("DOLAR");
			carteiraBeneficiarioPJ = carteiraPjRepository.buscarCarteiraPessoaJuridica(beneficiarioPJ, carteiraBeneficiarioPJ.getMoeda());
			
			beneficiario.setPessoaJuridica(beneficiarioPJ);
			beneficiario.setCarteiraPJ(carteiraBeneficiarioPJ);
			beneficiario.setTipoPessoa(PESSOA_JURIDICA);			
		}
		return beneficiario;
	}

	/**
	 * Metodo responsavel por enriquecer os dados do depositante
	 * para realizar a remessa
	 * @param Remessa remessa
	 * @return String 
	 * @throws ParseException 
	 */
	private Cliente enriquecerDepositante(Remessa remessa) throws ParseException {
		Cliente depositante = new Cliente();
		PessoaFisica depositantePF = new PessoaFisica();
		PessoaJuridica depositantePJ = new PessoaJuridica();				
		CarteiraFisica carteiraDepositantePF = new CarteiraFisica();
		CarteiraJuridica carteiraDepositantePJ = new CarteiraJuridica();
							
		if(remessa.getDepositante().length() == TAMANHO_CPF) {			
			depositantePF = pfRepository.buscarPessoaFisicaPorCPF(remessa.getDepositante());
			
			carteiraDepositantePF.setPessoaFisica(depositantePF);
			carteiraDepositantePF.setMoeda("REAL");
			carteiraDepositantePF = carteiraPfRepository.buscarCarteiraPessoaFisica(depositantePF, carteiraDepositantePF.getMoeda());
			
			//Regra Negocio : PF’s possuem um limite de 10 mil reais transacionados por dia.
			atualizaDataUltimaTransacaoPF(carteiraDepositantePF);				
			if(carteiraDepositantePF.getAcumuladoDia() == null) {
				carteiraDepositantePF.setAcumuladoDia(new BigDecimal(0));
			}
			
			depositante.setPessoaFisica(depositantePF);
			depositante.setCarteiraPF(carteiraDepositantePF);
			depositante.setTipoPessoa(PESSOA_FISICA);
		
		}else {			
			depositantePJ = pjRepository.buscarPessoaJuridicaPorCNPJ(remessa.getDepositante());
			
			carteiraDepositantePJ.setPessoaJuridica(depositantePJ);
			carteiraDepositantePJ.setMoeda("REAL");
			carteiraDepositantePJ = carteiraPjRepository.buscarCarteiraPessoaJuridica(depositantePJ, carteiraDepositantePJ.getMoeda());
			
			//Regra de Negocio: PJ’s possuem um limite de 50 mil reais transacionados por dia.
			atualizaDataUltimaTransacaoPJ(carteiraDepositantePJ);
			if(carteiraDepositantePF.getAcumuladoDia() == null) {
				carteiraDepositantePF.setAcumuladoDia(new BigDecimal(0));
			}
			
			depositante.setPessoaJuridica(depositantePJ);
			depositante.setCarteiraPJ(carteiraDepositantePJ);
			depositante.setTipoPessoa(PESSOA_JURIDICA);
			
		}
		return depositante;
	}

	/**
	 * Metodo responsavel validar a data da ultima transacao e atualizar data e limite para validar o limite diario
	 * @param CarteiraFisica carteiraDepositantePF
	 */
	private void atualizaDataUltimaTransacaoPF(CarteiraFisica carteiraDepositantePF) throws ParseException {
		if(carteiraDepositantePF.getUltimaTransacao() != null ){				
						
			Date dataBanco = sdf.parse(sdf.format(carteiraDepositantePF.getUltimaTransacao())); 
			Date hoje = sdf.parse(sdf.format(dataAtual)); 
							            
			if(dataBanco.before(hoje)){
				carteiraDepositantePF.setUltimaTransacao(new Date());
				carteiraDepositantePF.setAcumuladoDia(new BigDecimal(0));
			}					            
		}else {				
			carteiraDepositantePF.setUltimaTransacao(new Date());
		}
	}
	
	/**
	 * Metodo responsavel validar a data da ultima transacao e atualizar data e limite para validar o limite diario
	 * @param CarteiraJuridica carteiraDepositantePJ
	 */
	private void atualizaDataUltimaTransacaoPJ(CarteiraJuridica carteiraDepositantePJ) throws ParseException {
		if(carteiraDepositantePJ.getUltimaTransacao() != null ){				
			
			Date dataBanco = sdf.parse(sdf.format(carteiraDepositantePJ.getUltimaTransacao())); 
			Date hoje = sdf.parse(sdf.format(dataAtual)); 
							            
			if(dataBanco.before(hoje)){
				carteiraDepositantePJ.setUltimaTransacao(new Date());
				carteiraDepositantePJ.setAcumuladoDia(new BigDecimal(0));
			}					            
		}else {				
			carteiraDepositantePJ.setUltimaTransacao(new Date());
		}
	}
	
	/**
	 * Metodo responsavel debitar o valor na conta do depositante
	 * para realizar a remessa
	 * @param Remessa remessa, Cliente beneficiario
	 * @throws NegocioException 
	 */
	public void debitar(Remessa remessa, Cliente depositante) throws NegocioException {
		BigDecimal valorBase = new BigDecimal(remessa.getValor().replace(",", "."));
		BigDecimal valorAtual = new BigDecimal(0);
		
		if(depositante.getTipoPessoa().equals(PESSOA_FISICA)) {
			
			//Regra de Negocio : Validar se o usuário tem saldo antes da remessa.
			if(depositante.getCarteiraPF().getValor().doubleValue() >  valorBase.doubleValue()) {
				valorAtual = depositante.getCarteiraPF().getValor().subtract(valorBase);
				depositante.getCarteiraPF().setValor(valorAtual);
				carteiraPfRepository.save(depositante.getCarteiraPF());				
			}else {
				 throw new NegocioException("O depositante ( "+ depositante.getPessoaFisica().getNome() +" ) não tem saldo para realizar a remessa de "+ valorBase.doubleValue());
			}
			
		}else {
			//Regra de Negocio : Validar se o usuário tem saldo antes da remessa.
			if(depositante.getCarteiraPJ().getValor().doubleValue() > valorBase.doubleValue()) {
				valorAtual = depositante.getCarteiraPJ().getValor().subtract(valorBase);
				depositante.getCarteiraPJ().setValor(valorAtual);
				carteiraPjRepository.save(depositante.getCarteiraPJ());
			}else {
				throw new NegocioException("O depositante ( "+ depositante.getPessoaJuridica().getNome() +" ) não tem saldo para realizar a remessa de "+ valorBase.doubleValue());
			}
			
		}		
	}
	
	/**
	 * Metodo responsavel por creditar o valor na conta do beneficiario
	 * para realizar a remessa
	 * @param Remessa remessa, Cliente beneficiario
	 */
	public void creditar(Remessa remessa, Cliente beneficiario) {
		BigDecimal valorAtual = new BigDecimal(0);
		
		if(beneficiario.getTipoPessoa().equals(PESSOA_FISICA)) {
			valorAtual = beneficiario.getCarteiraPF().getValor().add(remessa.getValorConvertido());
			beneficiario.getCarteiraPF().setValor(valorAtual);
			carteiraPfRepository.save(beneficiario.getCarteiraPF());
		}else {
			valorAtual = beneficiario.getCarteiraPJ().getValor().add(remessa.getValorConvertido());
			beneficiario.getCarteiraPJ().setValor(valorAtual);
			carteiraPjRepository.save(beneficiario.getCarteiraPJ());
		}				
	}
}
