package com.platyduck.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${uplaod.path}")
    private String uploadPath;

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
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        System.out.println("FileController");
        file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/file/fileUpload";
    }

}
