package br.com.spingboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.spingboot.curso_jdev_treinamento.repository.UsuarioRepository;
import br.com.springboot.curso_jdev_treinamento.model.Usuario;



@RestController
public class UsuarioController {

	/* injentando as dependencias */
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping(value = "/listartodos")
	@ResponseBody /* Retorna os dados para o corpo da resposta */
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();/* executa a consulta no banco */

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/* retorna uma lista em json */
	}

	@PostMapping(value = "/salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario respostaUsuario = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(respostaUsuario, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/deleta")
	@ResponseBody
	public ResponseEntity<String> deleta(@RequestParam Long id) {

		usuarioRepository.deleteById(id);

		return new ResponseEntity<String>("Usuario deletado", HttpStatus.OK);
	}

	@GetMapping(value = "/buscaid")
	@ResponseBody
	public ResponseEntity<Usuario> listaUsuario(@RequestParam(name = "id") Long id) {

		Usuario usuarioPesquisado = usuarioRepository.findById(id).get();

		return new ResponseEntity<Usuario>(usuarioPesquisado, HttpStatus.OK);
	}

	@PutMapping(value = "/atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id do usuario não informado", HttpStatus.OK);
		}

		Usuario respostaUsuario = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(respostaUsuario, HttpStatus.OK);
	}

	@GetMapping(value = "/buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome) {
		/* trim retira os espaços */
		List<Usuario> usuarioPesquisado = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuarioPesquisado, HttpStatus.OK);
	}

}
