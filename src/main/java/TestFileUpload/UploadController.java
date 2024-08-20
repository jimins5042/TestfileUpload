package TestFileUpload;


import TestFileUpload.service.UploadS3;
import TestFileUpload.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/spring")
public class UploadController {

    private final ImageMapper imageMapper;
    private final UploadService uploadService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam MultipartFile file, HttpServletRequest request,
                           Model model) throws IOException {

        log.info("request={}", request);
        log.info("itemName={}", itemName);
        log.info("multipartFile={}", file);

        Image image = uploadService.uploadFile(file);

        model.addAttribute("imageLink", image.getImageLink());

        return "showImage";
    }

    @GetMapping("/imageList")
    public String imageList(Model model) {
        List<Image> list = imageMapper.findAll();

        model.addAttribute("items", list);

        return "imageList";
    }

    @GetMapping("/imageList/{id}")
    public String item(@PathVariable String id, Model model) {
        log.info("itemId={}", id);

        String imageLink = imageMapper.findImageByUuid(id);
        log.info("imageLink={}", imageLink);
        model.addAttribute("imageLink", imageLink);


        return "showImage";
    }

}
