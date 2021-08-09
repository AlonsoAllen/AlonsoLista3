package dev1.alonso.demo.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev1.alonso.demo.ContatoRepository.ContatoRepository;
import dev1.alonso.demo.model.Contato;


@Controller
public class ContatoController {   
	
	@Autowired
	private ContatoRepository contatos;
	
	@RequestMapping(value="/cadastrarContato", method=RequestMethod.GET)
	public String Formulario() {
		return "viewContatos/FormularioContato.html";
	}//página principal, onde exibe o formulário de contatos
	
	@RequestMapping(value="/cadastrarContato", method=RequestMethod.POST)
	public String Formulario(Contato contato) {
		contatos.save(contato);
		return "redirect:/cadastrarContato";
	}//ao apertar o botão Salvar, executa o método post e salva o contato
	
	
	
	@RequestMapping(value="contatos/{id}", method=RequestMethod.GET)
	public ResponseEntity<Contato> BuscarContato(@PathVariable Long id){ 
		Contato contatoExistente = contatos.getById(id);
	    	try {
	    		return ResponseEntity.ok(contatoExistente);
			} catch (Exception e) {
	    		return ResponseEntity.notFound().build();
			}
	    }//busca um contato em especifico
	
	@GetMapping
	@ResponseBody
	@RequestMapping("/contatos")
	public List<Contato> BuscarContato(){ 
		return contatos.findAll();
	    }//retorna a lista de contatos. Havia montado uma MV mas não consegui popular os elementos na página html, então acabava ficando vazio. Só consegui retornar usando JSON
	
	
	@RequestMapping(value="/contatos/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> DeletarContato(@PathVariable Long id) {
		Contato contato = contatos.getById(id);
		
		if (contato == null) {
			return ResponseEntity.notFound().build();
		}
		
		contatos.delete(contato);
		
		return ResponseEntity.noContent().build();
	}//deleta o contato com o id passado
	
	@RequestMapping(value="/alterarContatos/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Contato> AlterarContato (@PathVariable Long id, @RequestBody Contato contato) {
    	Contato contatoExistente = contatos.getById(id);
    	BeanUtils.copyProperties(contato, contatoExistente, "id"); // serve para usar somente o id que já está no banco
    	
    	contatoExistente = contatos.save(contatoExistente);
    	
    	return ResponseEntity.ok(contatoExistente);
    }//altera o contato, com as informações passadas no corpo do JSON
		
}