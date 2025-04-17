package team.logica_populi.dragonscore.base.logic.generators;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.logic.*;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Question generator to load questions for set P.
 * Needs to have a file under {@code DragonScore:Probability/ReasoningRecords.json}.
 */
public class ProbabilityReasoningGenerator implements QuestionGenerator {

    private static final TypeToken<List<ProbabilityReasoningRecord>> RECORDS_TYPE = new TypeToken<>(){};

    private final List<ProbabilityReasoningRecord> records;

    @Nullable
    private ProbabilityReasoningRecord cache;

    /**
     * Create a default {@code ProbabilityReasoningGenerator}.
     */
    public ProbabilityReasoningGenerator() {
        records = JsonRegistry.getInstance().getGson().fromJson(new ResourceLocation("DragonScore:Probability/ReasoningRecords.json").tryGetResource(), RECORDS_TYPE);
    }

    private String makeQuestionText(ProbabilityReasoningRecord record, boolean askCorrect) {
        if (!askCorrect) {
            return "<p>Which type of reasoning does this illustrate?</p><pre style='tab-size:4;'>" + record.question() + "</pre>";
        } else {
            return "<p>Is this a correct form of " + record.type().name +"?</p><pre style='tab-size:4;'>" + record.question() + "</pre>";
        }
    }

    @Override
    public Question getNextQuestion() {
        if (cache != null) {
            String text = makeQuestionText(cache, true);
            YesNoQuestion question = new YesNoQuestion(text, cache.correct());
            cache = null;
            return question;
        } else {
            cache = records.get((int) Math.floor(Math.random() * records.size()));
            String text = makeQuestionText(cache, false);
            ArrayList<Answer> answers = new ArrayList<>();
            for (ProbabilityReasoningRecord.ReasoningType value : ProbabilityReasoningRecord.ReasoningType.values()) {
                answers.add(new Answer(value.name, value == cache.type()));
            }
            return new BaseQuestion(text, answers);
        }
    }
}
