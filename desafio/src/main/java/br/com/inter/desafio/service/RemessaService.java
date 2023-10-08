package br.com.inter.desafio.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inter.desafio.dto.Cliente;
import br.com.inter.desafio.dto.Cotacao;
import br.com.inter.desafio.dto.Remessa;
import br.com.inter.desafio.entity.CarteiraFisica;
import br.com.inter.desafio.entity.CarteiraJuridica;
import br.com.inter.desafio.entity.PessoaFisica;
import br.com.inter.desafio.entity.PessoaJuridica;
import br.com.inter.desafio.repository.CarteiraFisicaRepository;
import br.com.inter.desafio.repository.CarteiraJuridicaRepository;
import br.com.inter.desafio.repository.PessoaFisicaRepository;
import br.com.inter.desafio.repository.PessoaJuridicaRepository;

/**
 * Classe responsavel por implementar as regras de negocio da remessa 
 * @author Júlio Silva
 *
 */
@Service
public class RemessaService extends ServiceBase {

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
	 */
	public String efetuarRemessa(Remessa remessa) {
		
		Cliente depositante = enriquecerDepositante(remessa);
		Cliente beneficiario = enriquecerBeneficiario(remessa);
		
		debitar(remessa, depositante);
		creditar(remessa, beneficiario);
		
		
		BigDecimal cotacaoDia = obterCotacao();
		
		
		return "OK";
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
		RestTemplate restTemplate= new RestTemplate();
				
		String url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='"
				+ "05-10-2023" //TODO : mudar para a data atual
				+ "'&$top=10&$skip=0&$format=json&$select=cotacaoCompra";
		
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).build();		
		HttpEntity<String> request = new HttpEntity<>(null);//header
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, String.class);
		
		if(response.getStatusCodeValue()==200 ) {				
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
			carteiraBeneficiarioPF.setMoeda(remessa.getMoeda());
			carteiraBeneficiarioPF = carteiraPfRepository.buscarCarteiraPessoaFisica(beneficiarioPF, remessa.getMoeda());
			
			beneficiario.setPessoaFisica(beneficiarioPF);
			beneficiario.setCarteiraPF(carteiraBeneficiarioPF);
			beneficiario.setTipoPessoa(PESSOA_FISICA);
	
		}else {
			beneficiarioPJ = pjRepository.buscarPessoaJuridicaPorCNPJ(remessa.getBeneficiario());
				
			carteiraBeneficiarioPJ.setPessoaJuridica(beneficiarioPJ);
			carteiraBeneficiarioPJ.setMoeda(remessa.getMoeda());
			carteiraBeneficiarioPJ = carteiraPjRepository.buscarCarteiraPessoaJuridica(beneficiarioPJ, remessa.getMoeda());
			
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
	 */
	private Cliente enriquecerDepositante(Remessa remessa) {
		Cliente depositante = new Cliente();
		PessoaFisica depositantePF = new PessoaFisica();
		PessoaJuridica depositantePJ = new PessoaJuridica();				
		CarteiraFisica carteiraDepositantePF = new CarteiraFisica();
		CarteiraJuridica carteiraDepositantePJ = new CarteiraJuridica();
							
		if(remessa.getDepositante().length() == TAMANHO_CPF) {			
			depositantePF = pfRepository.buscarPessoaFisicaPorCPF(remessa.getDepositante());
			
			carteiraDepositantePF.setPessoaFisica(depositantePF);
			carteiraDepositantePF.setMoeda(remessa.getMoeda());
			carteiraDepositantePF = carteiraPfRepository.buscarCarteiraPessoaFisica(depositantePF, remessa.getMoeda());
			
			depositante.setPessoaFisica(depositantePF);
			depositante.setCarteiraPF(carteiraDepositantePF);
			depositante.setTipoPessoa(PESSOA_FISICA);
		
		}else {			
			depositantePJ = pjRepository.buscarPessoaJuridicaPorCNPJ(remessa.getDepositante());
			
			carteiraDepositantePJ.setPessoaJuridica(depositantePJ);
			carteiraDepositantePJ.setMoeda(remessa.getMoeda());
			carteiraDepositantePJ = carteiraPjRepository.buscarCarteiraPessoaJuridica(depositantePJ, remessa.getMoeda());
			
			depositante.setPessoaJuridica(depositantePJ);
			depositante.setCarteiraPJ(carteiraDepositantePJ);
			depositante.setTipoPessoa(PESSOA_JURIDICA);
			
		}
		return depositante;
	}
	
	/**
	 * Metodo responsavel debitar o valor na conta do depositante
	 * para realizar a remessa
	 * @param Remessa remessa, Cliente beneficiario
	 */
	public void debitar(Remessa remessa, Cliente depositante) {
		BigDecimal valorAtual = new BigDecimal(0);
		BigDecimal valorADebitar = new BigDecimal(remessa.getValor().replace(",", "."));
		
		if(depositante.getTipoPessoa().equals(PESSOA_FISICA)) {
			valorAtual = depositante.getCarteiraPF().getValor().subtract(valorADebitar);
			depositante.getCarteiraPF().setValor(valorAtual);
			carteiraPfRepository.save(depositante.getCarteiraPF());
		}else {
			valorAtual = depositante.getCarteiraPJ().getValor().subtract(valorADebitar);
			depositante.getCarteiraPJ().setValor(valorAtual);
			carteiraPjRepository.save(depositante.getCarteiraPJ());
		}		
	}
	
	/**
	 * Metodo responsavel por creditar o valor na conta do beneficiario
	 * para realizar a remessa
	 * @param Remessa remessa, Cliente beneficiario
	 */
	public void creditar(Remessa remessa, Cliente beneficiario) {
		BigDecimal valorAtual = new BigDecimal(0);
		BigDecimal valorADebitar = new BigDecimal(remessa.getValor().replace(",", "."));
		
		if(beneficiario.getTipoPessoa().equals(PESSOA_FISICA)) {
			valorAtual = beneficiario.getCarteiraPF().getValor().add(valorADebitar);
			beneficiario.getCarteiraPF().setValor(valorAtual);
			carteiraPfRepository.save(beneficiario.getCarteiraPF());
		}else {
			valorAtual = beneficiario.getCarteiraPJ().getValor().add(valorADebitar);
			beneficiario.getCarteiraPJ().setValor(valorAtual);
			carteiraPjRepository.save(beneficiario.getCarteiraPJ());
		}				
	}
}
