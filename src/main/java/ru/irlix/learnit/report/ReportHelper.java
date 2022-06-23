package ru.irlix.learnit.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.irlix.learnit.config.S3Config;
import ru.irlix.learnit.dto.response.result.ReportResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.service.helper.FileHelper;
import ru.irlix.learnit.util.client.S3Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportHelper {

    @Value("${fontPath}")
    private String FONT;
    private static String USER_INFO = "Пользователь: %s %s";
    private static String TEST_INFO = "Название теста: %s";
    private static String TOPIC_INFO = "Направление: %s";
    private static String QUESTION_DESCRIPTION = "Вопрос: %s";

    private final FileHelper fileHelper;
    private final S3Client s3Client;
    private final S3Config s3Config;

    public ReportResponse getReport(Result result) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String fileName = UUID.randomUUID() + "report_" + result.getId() + ".pdf";
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 20, Font.NORMAL);
            Paragraph userParagraph = createUserParagraph(result, font);
            Paragraph topicParagraph = createTopicParagraph(result, font);
            Paragraph testParagraph = createTestParagraph(font, TEST_INFO, result.getTest().getName());
            PdfPTable questionTable = createTable(result, font);
            document.add(userParagraph);
            document.add(topicParagraph);
            document.add(testParagraph);
            document.add(questionTable);
            document.close();
            File savedFile = fileHelper.saveFile(fileName, out.toByteArray());
            s3Client.uploadFile(savedFile);
            log.info(String.format("Report with key %s saved in S3", fileName));
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
        }
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setFilePath(String.format("%s/s3/%s/%s", s3Config.getUrl(), s3Config.getBucket(), fileName));
        return reportResponse;
    }

    private PdfPTable createTable(Result result, Font font) throws DocumentException {
        PdfPTable questionTable = new PdfPTable(2);
        questionTable.setWidthPercentage(70);
        questionTable.setWidths(new int[]{1, 3});
        questionTable.setSpacingBefore(15);
        for (Question question : result.getTest().getQuestions()) {
            fillQuestion(font, questionTable, question.getText());
            for (Variant variant : question.getVariants()) {
                fillVariant(font, questionTable, variant, result.getId());
            }
        }
        return questionTable;
    }

    private Paragraph createTestParagraph(Font font, String testInfo, String name) {
        Paragraph testParagraph = createParagraph();
        Chunk testInfoChunk = new Chunk(String.format(testInfo,
                name),
                font);
        testParagraph.add(testInfoChunk);
        return testParagraph;
    }

    private Paragraph createTopicParagraph(Result result, Font font) {
        Paragraph topicParagraph = createParagraph();
        Chunk topicInfoChunk = new Chunk(String.format(TOPIC_INFO,
                result.getTest().getTopic().getName()),
                font);
        topicParagraph.add(topicInfoChunk);
        return topicParagraph;
    }

    private Paragraph createUserParagraph(Result result, Font font) {
        Paragraph userParagraph = createParagraph();
        Chunk userInfoChunk = new Chunk(String.format(USER_INFO,
                result.getUser().getName(),
                result.getUser().getSurname()),
                font);
        userParagraph.add(userInfoChunk);
        return userParagraph;
    }

    private void fillVariant(Font font, PdfPTable table, Variant variant, Long resultId) {
        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase(variant.getIsRight() ? "верный" : "", font));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(variant.getText(), font));
        cell.setBackgroundColor(getCellColor(variant, resultId));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private BaseColor getCellColor(Variant variant, Long resultId) {
        List<Answer> answers = variant.getAnswers();
        for (Answer answer : answers) {
            if (answer.getQuestion().equals(variant.getQuestion()) && answer.getResult().getId().equals(resultId))
                return variant.getIsRight() ? new BaseColor(159, 226, 190)
                        : new BaseColor(246, 165, 165);
        }
        return BaseColor.WHITE;
    }

    private void fillQuestion(Font font, PdfPTable table, String description) {
        PdfPCell cell = new PdfPCell(new Phrase(String.format(QUESTION_DESCRIPTION, description), font));
        cell.setColspan(2);
        table.addCell(cell);
    }

    private Paragraph createParagraph() {
        Paragraph paragraph = new Paragraph();
        paragraph.setSpacingAfter(10);
        paragraph.setFirstLineIndent(1);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }

}
