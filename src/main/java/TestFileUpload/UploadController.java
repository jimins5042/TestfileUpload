package TestFileUpload;


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

    private final UploadS3 uploadS3;
    private final ImageMapper imageMapper;

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

        String uuid = UUID.randomUUID().toString();

        String fullPath = uploadS3.upload(file, uuid);

        Image image = new Image(fullPath, uuid, file.getOriginalFilename());
        log.info("fullPath={} \nuuid={} \nfileName = {} ", fullPath, uuid, file.getOriginalFilename());
        imageMapper.insertImageInfo(image);


        /*
        String fullPath = "";

        if (!file.isEmpty()) {

            fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullPath);
            file.transferTo(new File(fullPath));

        }
         */

        model.addAttribute("imageLink", image.getImageLink());

        return "showImage";
        //return "upload-form";
    }

    @GetMapping("/imageList")
    public String imageList(Model model) {
        List<Image> list = imageMapper.findAll();

        for (Image image : list) {
            System.out.println(image.getImageLink());
            System.out.println(image.getImageUuid());
            System.out.println(image.getImageDate());
            System.out.println(image.getImageName());
            System.out.println(image.getId());
        }

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
