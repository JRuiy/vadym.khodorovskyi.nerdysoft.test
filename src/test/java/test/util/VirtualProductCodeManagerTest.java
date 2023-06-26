package test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirtualProductCodeManagerTest {
    private VirtualProductCodeManager codeManager;

    @BeforeEach
    void setUp() {
        codeManager = VirtualProductCodeManager.getInstance();
    }

    @Test
    void checkIsSingletonInstance() {
        VirtualProductCodeManager checkSingleton1 = VirtualProductCodeManager.getInstance();
        VirtualProductCodeManager checkSingleton2 = VirtualProductCodeManager.getInstance();
        Assertions.assertEquals(checkSingleton1, checkSingleton2, "Your instance should be singleton");
    }

    @Test
    void userCode_addNullValue_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> codeManager.useCode(null),
                "Your code should be not null");
    }

    @Test
    void userCode_addEmptyValue_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> codeManager.useCode(""),
                "Your code should be not empty");
    }

    @Test
    void userCode_validValue_ok() {
        String code = "xxx";
        codeManager.useCode("xxx");
        Assertions.assertTrue(codeManager.getCodes().contains(code),
                "Code " + code + " doesn't exist in storage");
    }

    @Test
    void isCodeUsed_codeExist_ok() {
        String code = "xxx";
        codeManager.getCodes().add(code);
        Assertions.assertTrue(codeManager.isCodeUsed(code),
                "Method should return true if code exist");
    }

    @Test
    void isCodeUsed_codeDoesntExist_ok() {
        String code = "yyy";
        Assertions.assertFalse(codeManager.isCodeUsed(code),
                "Method should return false if code doesn't exist");
    }
}