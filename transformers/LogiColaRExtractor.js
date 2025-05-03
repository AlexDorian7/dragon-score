// the mouse locations are harded coded for me using a maximized screen on a 1920x1080 monitor with a standard window bar, and standard one line windows tool bar at the bottom
// You will also need to install robotjs and clipboardy
import robot from "robotjs";
import clipboardy from "clipboardy";

function getClipboardText() {
    const text = clipboardy.readSync();
    return text;
}

function closeTextWindow() {
    robot.moveMouse(1219, 374);
    robot.mouseClick();
}

function copyTextWindow() {
    robot.moveMouse(1129, 415);
    robot.mouseClick();
}

function refocusMainWindow() {
    robot.moveMouse(64, 64);
    robot.mouseClick();
}

function openTextWindow() {
    robot.keyToggle("alt", "down");
    robot.keyTap("c");
    robot.keyToggle("alt", "up");
}

function getAnswer() {
    robot.moveMouse(1889, 32);
    robot.mouseClick();
}

function nextQuestion() {
    robot.keyTap("space");
}

function waitForMilliseconds(milliseconds) {
    return new Promise(resolve => setTimeout(resolve, milliseconds));
}

function wait() {
    return waitForMilliseconds(10);
}

async function getQuestion() {
    refocusMainWindow();
    await wait();
    getAnswer();
    await wait();
    openTextWindow();
    await waitForMilliseconds(1000); // The sub windo is extremely slow to open
    copyTextWindow();
    await waitForMilliseconds(250); // Make sure it is copied to the clipboard
    closeTextWindow();
    await wait();
    refocusMainWindow();
    await wait();
    nextQuestion();
    return getClipboardText();
}


const ret = {
    requires: [],
    terms: [],
    lessons: [
        {
            id: "week6",
            name: "Week 6 Fallacies",
            description: "PLEASE CHANGE ME",
            randomize: false,
            pointsRequired: 100,
            questionGenerators: [],
            staticQuestions: []
        },
    ],
    forms: []
};

for (let i = 0; i < 100; i++) {
    const text = await getQuestion();
    const blocks = text.split("\r\n\r\n");
    // console.log(blocks);
    if (blocks.length < 3) { // Not enough text blocks
        i--; // retry
        continue;
    }
    if (!blocks[0].startsWith("LogiCola")) { // Most likely copied wrong text
        i--; // retry
        continue;
    }
    if (blocks[1].indexOf("Alex") != -1 || blocks[1].indexOf("Dollar") != -1) { // This question used my name. We will ignore it
        i--; // retry
        continue;
    }
    const question = blocks[1].replace(/\r?\n/g, "").trim();
    let ans;
    if (blocks[3].trim().substring(28, 30) === "") {
        ans = [blocks[4].trim().substring(28, 30)];
        // console.log(blocks[4].trim());
    } else {
        ans = [blocks[3].trim().substring(28, 30)];
        // console.log(blocks[3].trim());
    }


    let other;
    for (let i = 4; i < blocks.length; i++) {
        if (blocks[i].trim().startsWith("This violates") && !blocks[i].trim().startsWith("This violates (") && !blocks[i].trim().startsWith("This violates clauses")) {
            other = blocks[i].trim();
            break;
        }
    }

    if (other != undefined) {
        ans = [];
        if (other.match(/[^A-Za-z]aa[^A-Za-z]/) !== null) ans.push("aa");
        if (other.match(/[^A-Za-z]ac[^A-Za-z]/) !== null) ans.push("ac");
        if (other.match(/[^A-Za-z]ae[^A-Za-z]/) !== null) ans.push("ae");
        if (other.match(/[^A-Za-z]af[^A-Za-z]/) !== null) ans.push("af");
        if (other.match(/[^A-Za-z]ah[^A-Za-z]/) !== null) ans.push("ah");
        if (other.match(/[^A-Za-z]ai[^A-Za-z]/) !== null) ans.push("ai");
        if (other.match(/[^A-Za-z]am[^A-Za-z]/) !== null) ans.push("am");
        if (other.match(/[^A-Za-z]bp[^A-Za-z]/) !== null) ans.push("bp");
        if (other.match(/[^A-Za-z]bw[^A-Za-z]/) !== null) ans.push("bw");
        if (other.match(/[^A-Za-z]ci[^A-Za-z]/) !== null) ans.push("ci");
        if (other.match(/[^A-Za-z]cq[^A-Za-z]/) !== null) ans.push("cq");
        if (other.match(/[^A-Za-z]fs[^A-Za-z]/) !== null) ans.push("fs");
        if (other.match(/[^A-Za-z]ge[^A-Za-z]/) !== null) ans.push("ge");
        if (other.match(/[^A-Za-z]op[^A-Za-z]/) !== null) ans.push("op");
        if (other.match(/[^A-Za-z]pc[^A-Za-z]/) !== null) ans.push("pc");
        if (other.match(/[^A-Za-z]ph[^A-Za-z]/) !== null) ans.push("ph");
        if (other.match(/[^A-Za-z]pw[^A-Za-z]/) !== null) ans.push("pw");
        if (other.match(/[^A-Za-z]sm[^A-Za-z]/) !== null) ans.push("sm");
    }

    // console.warn(i);
    // console.log(question);
    // console.log(ans);
    // console.log("");
    if (ans.length < 1) throw "BAD";
    if (ans[0] === "") throw "BAD";

    let ret1 = {
        question: `<p>What fallacy(s) does this illustrate?</p><pre style='tab-size:4;'>${question}</pre>`,
        answers: [
            {
                answer: "Appeal to authority",
                correct: ans.findIndex((val) => val === "aa") !== -1
            },
            {
                answer: "Appeal to crowd",
                correct: ans.findIndex((val) => val === "ac") !== -1
            },
            {
                answer: "Appeal to emotion",
                correct: ans.findIndex((val) => val === "ae") !== -1
            },
            {
                answer: "Appeal to force",
                correct: ans.findIndex((val) => val === "af") !== -1
            },
            {
                answer: "Ad hominem",
                correct: ans.findIndex((val) => val === "ah") !== -1
            },
            {
                answer: "Appeal to ignorance",
                correct: ans.findIndex((val) => val === "ai") !== -1
            },
            {
                answer: "Ambiguous",
                correct: ans.findIndex((val) => val === "am") !== -1
            },
            {
                answer: "Beside the point",
                correct: ans.findIndex((val) => val === "bp") !== -1
            },
            {
                answer: "Black and white",
                correct: ans.findIndex((val) => val === "bw") !== -1
            },
            {
                answer: "Circular",
                correct: ans.findIndex((val) => val === "ci") !== -1
            },
            {
                answer: "Complex question",
                correct: ans.findIndex((val) => val === "cq") !== -1
            },
            {
                answer: "False stereotype",
                correct: ans.findIndex((val) => val === "fs") !== -1
            },
            {
                answer: "Genetic",
                correct: ans.findIndex((val) => val === "ge") !== -1
            },
            {
                answer: "Opposition",
                correct: ans.findIndex((val) => val === "op") !== -1
            },
            {
                answer: "Pro-con",
                correct: ans.findIndex((val) => val === "pc") !== -1
            },
            {
                answer: "Post hoc",
                correct: ans.findIndex((val) => val === "ph") !== -1
            },
            {
                answer: "Part-whole",
                correct: ans.findIndex((val) => val === "pw") !== -1
            },
            {
                answer: "Straw man",
                correct: ans.findIndex((val) => val === "sm") !== -1
            }
        ]
    }
    ret.lessons[0].staticQuestions.push(ret1);
}

console.log(JSON.stringify(ret, null, 2).replace(/\\\\/g, "\\"));