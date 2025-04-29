const BF = [
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
    `*We may conclude that not |all who are scholarly are terrorists| - sinceterrorists are cruel,| andthose who are cruel aren't bashful,| andat least some who are bashful are scholarly.`,

    `A person isn't a convict unless he or she is generous.| It follows that\u001F*only those who are convicts are generous.`,
    `No realistic dentists are gentle.\u001FPeople who are bright are sometimes gentle.| This shows that\u001F*not everyone who is bright is a realistic dentist.`,
    `It is false that there are some remarkable druggists who aren't powerful. |So\u001F*everyone who is cheap must be powerful,| in view of the fact that\u001F*those who are cheap are always remarkable druggists. `,
    `*One or more poets in Missouri must be creative.| The evidence for this is that\u001Fwhoever is a poet in Mo is mean| and that\u001Fsome who are mean are creative.`,
    `*You aren't bitter| - because\u001Fyou're the toughest person in Mo| and\u001Fthe toughest person in Mo hurts plumbers.| Naturally\u001Fthose who hurt plumbers aren't bitter.`,
    `We may conclude that\u001F*not all who are fortunate are bandits| - since\u001Fbandits are cheap,| and\u001Fthose who are cheap aren't hideous, |and \u001Fat least some who are hideous are fortunate.`,
    `*The wildest customer is mediocre.| We know this because we know that\u001Fwhoever is a neighbor of Iron Man isn't mediocre| and that\u001Fthe wildest customer isn't a neighbor of Iron Man.`,
    `You're the brightest person in space| and\u001F*the brightest person in space doesn't know hypocrites.| This implies that\u001F*you must be miserable.| After all,\u001Fwhoever knows hypocrites isn't miserable.`,
    `*Not all who are naive are slow.| My reason for saying this is that\u001Fsome who are disgusting are slow| and\u001Fthose who are naive are never disgusting.`,
    `Relatives of Keith aren't sarcastic.\u100FThe cleanest pilot is a relative of Keith.| From this it follows that\u001F*the cleanest pilot isn't sarcastic.`,
    `No sociable dentists are terrified.\u100FNot all people who are sociable dentists are bold.| We may conclude that\u001F*people who are terrified are sometimes bold.`,
    `Whoever is a talented musician isn't slow.| But\u001Fno one is careful unless he or she is slow.| As a result,\u001F*not a single talented musician is careful.`,
    `One or more vicious pessimists aren't generous.| This proves that\u100F*not all who are generous are vicious pessimists.`,
    `Donna isn't a frightened reporter.| Of course\u100Fthe boldest person is a frightened reporter.| This proves that\u001F*Donna isn't the boldest person.`,
    `*Keith can't be remarkable.| I say this for three reasons.\u001F(1) Keith is a scholar.\u001F(2) No scholars are weak.| And finally\u001F(3) none but weak people are remarkable.`,
    `Because |the dullest person is a faithful Cloud watcher,| it must be that\u001F*Spider-Man is the dullest person| - since\u001FSpider-Man is a faithful Cloud watcher.`,
    `A person isn't a hideous pickpocket unless he or she is greedy.\u001FNot all who are bold are greedy.| Hence\u001F*some who are bold aren't hideous pickpockets.`,
    `At least one banker is frantic,| and\u001Fonly those who are slow are bankers.| It follows that\u001F*some frantic persons are slow.`,
    `*Mickey isn't the richest mascot in Florida.| This means that\u001F*Mickey can't be this friendly gator,| since\u001Fthis friendly gator is the richest mascot in Florida.`,
    `At least one smart person is humorous,| and\u001Fonly those who are smart are professors.| It follows that\u001F*some who are humorous are professors.`,
    `It is false that there is some romantic carpenter who isn't poetic.| Therefore\u001F*everyone who is strong is poetic,| since\u001Fpeople who are romantic carpenters are always strong.`,
    `Keith isn't a demented soldier.| And\u001Fdemented soldiers aren't poor.| All this implies that\u001F*Keith is terrified| - since we all know that\u001F*poor people are terrified.`,
    `A person isn't a careful foreigner unless he or she is sociable.\u001FNot all who are tough are sociable.| Then it must be that\u001F*some careful foreigners aren't tough.`,
    `Since |cautious diabetics are short| and\u001Fit's false that some short person is vicious,| as a result\u001F*no vicious person is a cautious diabetic.`,
    `*No bold person is frivolous.| For\u001Fcharitable druggists are bold| and\u001Fit's false that there is some charitable druggist who is frivolous.`
];

const EF = [
    `You wouldn't be frivolous if you were either cheap or not scholarly.| As a consequence,*you must be scholarly| - since clearlyyou're frivolous.`,
    `George isn't both a carpenter and also either strong or frightened.*George must be a carpenter,| since obviouslyGeorge isn't strong.`,
    `A necessary and sufficient condition for Donna being both cruel and realistic is that Donna isn't boastful.| Thus*Donna isn't cruel.| My evidence for this is thatDonna is boastful.| And obviouslyDonna isn't realistic.`,
    `It is sufficient that you be a dentist, in order for you to be short and remarkable.You aren't short.| It follows that*you can't be a dentist.`,
    `*These two facts prove to us that |Jim is poor.| First,Jim isn't both poor and not faithful.| Second,Jim is faithful.`
]

const array = []

EF.forEach(question => {
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