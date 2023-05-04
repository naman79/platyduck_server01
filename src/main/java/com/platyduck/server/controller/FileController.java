package com.platyduck.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/upload")
    public String file(Model model) {

        System.out.println("FileController");

        return "upload.html";
    }

    @GetMapping("/fileUpload")
    public String uploadForm(Model model) {

        System.out.println("FileController");

        return "uploadForm.html";
    }

    //fileUpload
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        System.out.println("FileController");
        

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/file/fileUpload";
    }

}
