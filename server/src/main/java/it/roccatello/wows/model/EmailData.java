package it.roccatello.wows.model;
import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailData {
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String from;
    private String subject;
    private String html;
    private String text;
    private List<File> attachments;
}
