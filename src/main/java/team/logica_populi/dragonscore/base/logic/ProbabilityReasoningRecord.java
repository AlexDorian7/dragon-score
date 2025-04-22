package team.logica_populi.dragonscore.base.logic;

/**
 * Record to contain information about a probability reasoning record
 * @param question The text for the syllogism
 * @param type The type of reasoning it is
 * @param correct If it is the correct form
 */
public record ProbabilityReasoningRecord(String question, ReasoningType type, boolean correct) {

    /**
     * Creates a default probability reasoning record
     * @param question The text for the argument
     * @param type The type of reasoning it is
     * @param correct If it is the correct form
     */
    public ProbabilityReasoningRecord {}

    /**
     * The type of the syllogism
     */
    public enum ReasoningType {
        ANALOGY("Analogy Syllogism"),
        SAMPLE_PROJECTION("Sample-Projection Syllogism"),
        STATISTICAL("Statistical Syllogism"),
        AGREEMENT("Agreement Method"),
        DIFFERENCE("Difference Method"),
        DISAGREEMENT("Disagreement Method"),
        VARIATION("Variation Method");

        public final String name;

        ReasoningType(String name) {
            this.name = name;
        }

    }
}
