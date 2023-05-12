package com.platyduck.server.controller;

import com.platyduck.server.domain.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

@Controller
@RequestMapping("/")
public class HelloController {

        @GetMapping("hello")
        public String index() {

            System.out.println("HelloController");

            return "hello.html";
        }

    @RequestMapping(value = "/call-api", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> callApi(
            @RequestBody Param param) {

        try {
            System.out.println("apiTarget: " + param.getData1());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile
}
