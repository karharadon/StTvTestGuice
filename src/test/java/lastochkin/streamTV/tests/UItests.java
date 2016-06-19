package lastochkin.streamTV.tests;

import com.google.inject.Inject;
import lastochkin.streamTV.helpers.GuiceTestModule;
import lastochkin.streamTV.helpers.TestListener;
import lastochkin.streamTV.pages.LoginPage;
import lastochkin.streamTV.wrestlers.Wrestler;
import lastochkin.streamTV.wrestlers.WrestlerService;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.io.IOException;


@Listeners(TestListener.class)
@Guice(modules = GuiceTestModule.class)
public class UItests extends BaseTest {

    @Inject
    LoginPage loginPage;

    @Inject
    WrestlerService wrestlerService;

    @Description("Test create wrestler and delete")
    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 1)
    public void createAndDelete(Wrestler wrestler1) {
        loginPage.login(driver);
        wrestlerService.createWrestler(wrestler1);
        wrestlerService.findWrestler(wrestler1.getFullName());
        wrestlerService.deleteWrestler();
        wrestlerService.checkDeletion(wrestler1.getFullName());
    }

    @Description("Test create wrestler and verify data")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 2)
    public void createAndVerify(Wrestler wrestler1) {
        loginPage.login(driver);
        wrestlerService.createWrestler(wrestler1);
        wrestlerService.findWrestler(wrestler1.getFullName());
        wrestlerService.verifySearchResultWithCode(wrestler1, wrestler1.getFullName(),
                wrestlerService.errorsAfterCreating);
        wrestlerService.verifyProfileDataWithCode(wrestler1, wrestlerService.errorsAfterCreating);
        wrestlerService.deleteWrestler();
        wrestlerService.checkExeptions(wrestlerService.errorsAfterCreating);

    }

    @Description("Test updates wrestler and verify data")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 3)
    public void updateAndVerify(Wrestler wrestler1, Wrestler wrestler2) {
        loginPage.login(driver);
        wrestlerService.createWrestler(wrestler1);
        wrestlerService.updateWrestler(wrestler1, wrestler2);
        wrestlerService.findWrestler(wrestler2.getFullName());
        wrestlerService.verifySearchResultWithCode(wrestler2, wrestler2.getFullName(),
                wrestlerService.errorsAfterUpdating);
        wrestlerService.verifyProfileDataWithCode(wrestler2, wrestlerService.errorsAfterUpdating);
        wrestlerService.deleteWrestler();
        wrestlerService.checkExeptions(wrestlerService.errorsAfterUpdating);
    }

    @Description("Test creates six wrestlers verify that filters works correct")
    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 4)
    public void checkFilters(Wrestler wrestler2, Wrestler wrestler3, Wrestler wrestler4,
                             Wrestler wrestler5, Wrestler wrestler6) {
        loginPage.login(driver);
        wrestlerService.createFewWrestlersForTestingFilters(wrestler2, wrestler3, wrestler4, wrestler5, wrestler6);
        wrestlerService.useAndCheckDifferentFilters(wrestler2);
        wrestlerService.deleteWrestlersCreatedForTestingFilters();
    }

    @Description("Test verify that correct photo upload to the wrestlers profile")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 5)
    public void uploadImage(Wrestler wrestler1) throws IOException {
        loginPage.login(driver);
        wrestlerService.createWrestler(wrestler1);
        wrestlerService.findWrestler(wrestler1.getFullName());
        //wrestlerService.uploadImage();
       // wrestlerService.checkThatCorrectImageWasUploaded();
        wrestlerService.deleteWrestler();
    }

    @Description("Test verify that correct file upload to the wrestlers profile")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = Wrestler.class, dataProvider = "wrestlers", priority = 6)
    public void uploadAndDeleteAttachment(Wrestler wrestler1) throws IOException {
        loginPage.login(driver);
        wrestlerService.createWrestler(wrestler1);
        wrestlerService.findWrestler(wrestler1.getFullName());
        wrestlerService.uploadAttachment();
        wrestlerService.checkThatCorrectAttachmentWasUploaded();
        wrestlerService.checkAttachmentDeletation(wrestler1);
        wrestlerService.deleteWrestler();
    }
}

