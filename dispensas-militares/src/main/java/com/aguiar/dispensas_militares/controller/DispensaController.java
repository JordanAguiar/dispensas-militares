package com.aguiar.dispensas_militares.controller;

import com.aguiar.dispensas_militares.model.Dispensa;
import com.aguiar.dispensas_militares.model.Militar;
import com.aguiar.dispensas_militares.service.DispensaService;
import com.aguiar.dispensas_militares.service.MilitarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dispensas")
public class DispensaController {

    private final DispensaService dispensaService;
    private final MilitarService militarService;

    @GetMapping("/militar/{militarId}")
    public String listarPorMilitar(@PathVariable Long militarId, Model model) {
        Militar militar = militarService.buscarPorId(militarId);
        model.addAttribute("militar", militar);
        model.addAttribute("dispensas", dispensaService.listarPorMilitar(militar));
        model.addAttribute("dispensa", new Dispensa());
        return "dispensas/listar";
    }

    @PostMapping("/salvar/{militarId}")
    public String salvar(@PathVariable Long militarId,
                         @ModelAttribute Dispensa dispensa,
                         @RequestParam("arquivo") MultipartFile arquivo) throws Exception {
        Militar militar = militarService.buscarPorId(militarId);
        dispensa.setMilitar(militar);
        dispensaService.salvar(dispensa, arquivo);
        return "redirect:/dispensas/militar/" + militarId;
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> visualizarPdf(@PathVariable Long id) {
        Dispensa dispensa = dispensaService.buscarPorId(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + dispensa.getNomeArquivoPdf() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(dispensa.getPdf());
    }

    @GetMapping("/deletar/{id}/{militarId}")
    public String deletar(@PathVariable Long id, @PathVariable Long militarId) {
        dispensaService.deletar(id);
        return "redirect:/dispensas/militar/" + militarId;
    }
}