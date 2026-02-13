package fr.lastril.onepiecemod.translate;

public class TranslationParam {

    private final String paramKey;
    private final Object value;

    private TranslationParam(String paramKey, Object value) {
        this.paramKey = paramKey;
        this.value = value;
    }

    public String getParamKey() {
        return this.paramKey;
    }

    public Object getValue() {
        return this.value;
    }

    public static TranslationParam from(String key, Object value) {
        return new TranslationParam(key, value);
    }

}
