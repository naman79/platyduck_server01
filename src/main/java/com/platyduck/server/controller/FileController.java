package com.platyduck.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${uplaod.path}")
    private String uploadPath;

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        File dirFile = new File(uploadPath);
        File[] fileList = dirFile.listFiles();
        List<String> ret = new ArrayList<>();

        if(fileList == null) {
            return "uploadForm.html";
        }
        
        Arrays.sort(fileList);
        Arrays.stream(fileList).sorted().filter(File::isFile).forEach( file -> {
            ret.add(file.getName());
        });

//        for (File file : fileList) {
//
//            if(file.isFile()) {
//
//            }
//
//            if (file.isFile()) {
//                ret.add(file.getName());
//            }
//        }

        model.addAttribute("files", ret);

        return "uploadForm.html";
    }

    //fileUpload
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        System.out.println("FileController");
        file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/file/upload";
    }


    @GetMapping("/upload-ajax")
    public String uploadAjax(Model model) {
        File dirFile = new File(uploadPath);
        File[] fileList = dirFile.listFiles();
        List<String> ret = new ArrayList<>();

        if(fileList == null) {
            return "uploadForm.html";
        }

        Arrays.sort(fileList);
        Arrays.stream(fileList).sorted().filter(File::isFile).forEach( file -> {
            ret.add(file.getName());
        });

//        for (File file : fileList) {
//
//            if(file.isFile()) {
//
//            }
//
//            if (file.isFile()) {
//                ret.add(file.getName());
//            }
//        }

        model.addAttribute("files", ret);

        return "uploadAjax.html";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("uploadfile") MultipartFile uploadfile) {

        try {
            // Get the filename and build the local file path (be sure that the
            // application have write permissions on such directory)
            String filename = uploadfile.getOriginalFilename();
            String directory = uploadPath;
            String filepath = Paths.get(directory, filename).toString();

            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile

}
