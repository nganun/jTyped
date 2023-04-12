package com.github.nganun.jtyped.excel;

public class DictEntity {

    private String word;
    private String phonetic;
    private String tense;
    private String description;

    public DictEntity(String word, String phonetic, String tense, String description) {
        this.word = word;
        this.phonetic = phonetic;
        this.tense = tense;
        this.description = description;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DictEntity{" +
                "word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", tense='" + tense + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
