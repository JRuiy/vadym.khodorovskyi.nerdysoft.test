package test.util;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class VirtualProductCodeManager {
    private static VirtualProductCodeManager manager = new VirtualProductCodeManager();
    private Set<String> codes;

    private VirtualProductCodeManager() {
        codes = new HashSet<>();
    }

    public static VirtualProductCodeManager getInstance() {
        return manager;
    }

    public void useCode(String code) {
        checkCode(code);
        codes.add(code);
    }

    public boolean isCodeUsed(String code) {
        return codes.contains(code);
    }

    private void checkCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new RuntimeException("Your code is wrong: " + code);
        }
    }
}