package util.Listeners;

import io.qameta.allure.Attachment;
        import io.restassured.filter.log.LogDetail;
        import io.restassured.filter.log.RequestLoggingFilter;
        import io.restassured.filter.log.ResponseLoggingFilter;
        import org.testng.ITestContext;
        import org.testng.ITestResult;
        import org.testng.TestListenerAdapter;

        import java.io.ByteArrayOutputStream;
        import java.io.PrintStream;

        import static io.restassured.RestAssured.filters;

public class allureApiTestListener extends TestListenerAdapter {

    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestVar = new PrintStream(request, true);
    private PrintStream responseVar = new PrintStream(response, true);


    public void onStart(ITestContext iTestContext) {
        filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar));
    }

    public void onTestSuccess(ITestResult iTestResult) {
        logRequest(request);
        logResponse(response);
    }

    public void onTestFailure(ITestResult iTestResult) {
        onTestSuccess(iTestResult);
    }

    public void onTestSkipped(ITestResult iTestResult) {
        onTestSuccess(iTestResult);
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}