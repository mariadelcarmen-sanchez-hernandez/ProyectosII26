package com.generaction.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generaction.backend.dto.MensajeChatDTO;
import com.generaction.backend.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/enviar")
    public ResponseEntity<MensajeChatDTO> enviar(@RequestBody MensajeChatDTO dto) {
        return ResponseEntity.ok(chatService.enviarMensaje(dto));
    }

    @GetMapping("/visita/{idVisita}")
    public ResponseEntity<List<MensajeChatDTO>> obtenerTodos(@PathVariable Long idVisita) {
        return ResponseEntity.ok(chatService.obtenerMensajesPorVisita(idVisita));
    }

    @GetMapping("/visita/{idVisita}/nuevos")
    public ResponseEntity<List<MensajeChatDTO>> obtenerNuevos(
            @PathVariable Long idVisita,
            @RequestParam String desde) {
        return ResponseEntity.ok(chatService.obtenerMensajesNuevos(idVisita, LocalDateTime.parse(desde)));
    }
}
