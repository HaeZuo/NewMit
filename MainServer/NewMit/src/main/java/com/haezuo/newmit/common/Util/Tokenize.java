package com.haezuo.newmit.common.Util;

import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenize {
    // Normalize
    public List<String> GetTokens(String str) {
        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(str);

        // Tokenize
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

        // Phrase Extraction
        List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);


        List<KoreanPhraseExtractor.KoreanPhrase> phrasesFilter = phrases.stream().filter(koreanPhrase -> koreanPhrase.text().indexOf(" ") == -1).collect(Collectors.toList());

        List<String> resultTokens = new ArrayList<>();
        for(KoreanPhraseExtractor.KoreanPhrase koreanPhrase : phrasesFilter)
            resultTokens.add(koreanPhrase.text());

        return resultTokens;
    }
}
