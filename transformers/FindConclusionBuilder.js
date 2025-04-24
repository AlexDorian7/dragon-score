const data = [
    `You're the cheapest person in Missouri| andthe cheapest person in Missouri doesn't forget professors.| This implies that*you must be talented.| After all,whoever forgets professors isn't talented.`,
    `*Tom is a scholarly professor.| How do I know this? Well,Tom is clean| andonly those who are clean are scholarly professors.`,
    `Since |prosperous convicts are weak| andit's false that some weak person is remarkable,| as a result*no remarkable person is a prosperous convict.`,
    `Keith isn't a dishonest soldier.| Anddishonest soldiers aren't cruel.| All this implies that*Keith is faithful| - since we all know thatcruel people are faithful.`,
    `You're the boldest person in Missouri| andthe boldest person in Missouri doesn't hate diabetics.| This implies that*you must be courageous.| After all,whoever hates diabetics isn't courageous.`,
    `*No smart person is brilliant.| Forconfused farmers are smart| andit's false that there is some confused farmer who is brilliant.`,
    `*One or more grocers in Missouri must be colorful.| The evidence for this is thatwhoever is a grocer in Missouri is bright| and thatsome who are bright are colorful.`,
    `*It's false that poetic persons are sometimes customers.| This follows from the factthat not a single customer is poetic.`,
    `*The cruelest guitarist is romantic.| We know this because we know thatwhoever is a friend of Madonna isn't romantic| and thatthe cruelest guitarist isn't a friend of Madonna.`,
    `It is false that there are some virtuous clowns who aren't demented.| So*everyone who is smart must be demented,| in view of the fact thatthose who are smart are always virtuous clowns.`,
    `*We may conclude that |not all who are talented are judges| - sincejudges are dull,| andthose who are dull aren't sociable,| andat least some who are sociable are talented.`,
    `No frivolous dentists are cautious.Not all people who are frivolous dentists are rough.| We may conclude that*people who are cautious are sometimes rough.`,
    `Whoever is a realistic prisoner isn't slow.| Butno one is frightened unless he or she is slow.| As a result,*not a single realistic prisoner is frightened.`,
    `Any powerful musician is cheerful.Not everyone who is wild is a powerful musician.| Hence*not everyone who is wild is cheerful.`,
    `One or more cowardly scientists aren't dishonest.| This proves that*not all who are dishonest are cowardly scientists.`,
    `It is false that there are some mediocre senators who aren't fortunate.| So*everyone who is poor must be fortunate,| in view of the fact thatthose who are poor are always mediocre senators.`,
    `*David is this gloomy person.| This follows becauseDavid is the roughest pickpocket in Missouri| andthis gloomy person is the roughest pickpocket in Missouri.`,
    `Any polite democrat is naive.Not everyone who is smart is a polite democrat.| Hence*not everyone who is smart is naive.`,
    `At least some wrestlers in Missouri are cheap.| Thus*some wrestlers in Missouri are demented,| since surelysome cheap persons are demented.`,
    `*We may conclude that not |all who are scholarly are terrorists| - sinceterrorists are cruel,| andthose who are cruel aren't bashful,| andat least some who are bashful are scholarly.`
];

const array = []

data.forEach(question => {
    parts = question.split("\u001F");
    let ret = {
        question: "Please find the conclusion in this argument.",
        answers: []
    }
    parts.forEach(part => {
        if (part.startsWith("*")) {
            ret.answers.push({
                answer: part.substring(1),
                correct: true
            });
        } else {
            ret.answers.push({
                answer: part,
                correct: false
            });
        }
    });
    array.push(ret);
});

console.log(JSON.stringify(array, null, 2));