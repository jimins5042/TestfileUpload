package TestFileUpload.controller;

import TestFileUpload.Image;
import TestFileUpload.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/spring")
public class DrawController {

    private final UploadService uploadService;

    @GetMapping("/draw")
    public String draw() {
        return "Canvas";
    }

    @PostMapping("/drawUpload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        Image image = uploadService.uploadFile(file);

        model.addAttribute("imageLink", image.getImageLink());

        return "showImage";
    }

}
