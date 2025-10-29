package com.gdp.backend.controller;

import com.gdp.backend.common.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This Controller class used for retrieving Project help guide information.
 * @author gdp
 *
 */
@RestController
@RequestMapping("help")
@Secured({"ROLE_Admin", "ROLE_Global Edit Access", "ROLE_Global View Access","ROLE_Executive"})
public class HelpController {

    @Value("${helpGuideFilePath}")
    private String helpGuideFilePath;

    /**
     * This API would be use for download the Help Guide.
     * @return Help Guide doc file.
     */
    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadHelpGuide() {

        StreamingResponseBody responseBody = response -> {
            InputStream inputStream = new ClassPathResource(helpGuideFilePath).getInputStream();
            byte[] bytes=new byte[1024];
            int length;
            while ((length=inputStream.read(bytes)) >= 0) {
                response.write(bytes, 0, length);
            }
            response.write(inputStream.read());
            inputStream.close();
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                        Constants.HELP_GUIDE_FILENAME +
                        ".pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }

}
