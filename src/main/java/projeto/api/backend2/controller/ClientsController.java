package projeto.api.backend2.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.backend2.service.ClientsService;

@RestController
@RequestMapping("/clientes/")
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @PostMapping("{id}/transacoes")
    public ResponseEntity<?> create(@RequestBody Map<String, String> body, @PathVariable long id) {
        return ResponseEntity.ok(clientsService.create(body, id));
    }

    @GetMapping("{id}/extrato")
    public ResponseEntity<?> extrato(@PathVariable Long id) {
        return ResponseEntity.ok(clientsService.extrato(id));
    }
}