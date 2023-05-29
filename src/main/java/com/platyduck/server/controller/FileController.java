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
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            return "uploadAjax.html";
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

    @GetMapping("/upload-filejson")
    public String uploadFileAndJson(Model model) {

        return "uploadFileJson.html";
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

    @RequestMapping(value = "/uploadFileJson", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFileJson(
            @RequestPart(value = "key") Map<String, Object>  requestMap,
            @RequestPart(value = "file") MultipartFile uploadfile) {

            return getMultipartData(requestMap, uploadfile);
    }


    @GetMapping("/upload-tcp")
    public String uploadTcp(Model model) {
        File dirFile = new File(uploadPath);
        File[] fileList = dirFile.listFiles();
        List<String> ret = new ArrayList<>();

        if(fileList == null) {
            return "uploadTcp.html";
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

        return "uploadTcp.html";
    }

    @PostMapping("/upload-tcp")
    public ResponseEntity<?> uploadTcp(
            @RequestPart(value = "key") Map<String, Object>  requestMap,
            @RequestPart(value = "file") MultipartFile uploadfile
    ) {

        String serverHost = "localhost"; // Replace with the server's host address
        int serverPort = 7171; // Replace with the server's port

        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to TCP server at " + serverHost + ":" + serverPort);

            String message = "Hello, server!";
            writer.println(message);
            System.out.println("Sent message to server: " + message);

            String response = reader.readLine();
            System.out.println("Received response from server: " + response);

        } catch (IOException e) {
            System.err.println("Error connecting to TCP server: " + e.getMessage());
            e.printStackTrace();
        }

        return getMultipartData(requestMap, uploadfile);
    }

    @GetMapping("/upload-websocket")
    public String uploadWebSocket(Model model) {
        File dirFile = new File(uploadPath);
        File[] fileList = dirFile.listFiles();
        List<String> ret = new ArrayList<>();

        if(fileList == null) {
            return "uploadTcp.html";
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

        return "uploadWebSocket.html";
    }

    @PostMapping("/upload-websocket")
    public ResponseEntity<?> uploadWebSocket(
            @RequestPart(value = "key") Map<String, Object>  requestMap,
            @RequestPart(value = "file") MultipartFile uploadfile
    ) throws IOException {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getMultipartData(Map<String, Object> requestMap, MultipartFile uploadfile) {
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
    }

}
