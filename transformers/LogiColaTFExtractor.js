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

function tryAnswer() {
    robot.keyTap("1");
}

function nextQuestion() {
    robot.keyTap("space");
}

function waitForMilliseconds(milliseconds) {
    return new Promise(resolve => setTimeout(resolve, milliseconds));
}

function wait() {
    return waitForMilliseconds(100);
}

async function getQuestion() {
    refocusMainWindow();
    await wait();
    tryAnswer();
    // Why is the incorrect text animation so slow..... And why does this animation prevent me from pressing Alt+C???
    await waitForMilliseconds(1000); // Wait for text animation to finish, cause Logicola animations hault rest of program
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
            id: "propositional_arguments_english",
            name: "Propositional Arguments - English",
            description: "PLEASE CHANGE ME",
            randomize: false,
            questionGenerators: [],
            staticQuestions: []
        },
    ],
    forms: []
};

for (let i = 0; i < 2; i++) {
    const text = await getQuestion();
    const blocks = text.split("\r\n\r\n");
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
    const question = blocks[1];
    const lines = blocks[2].split("\r\n");
    let correct = false;
    if (lines[0].indexOf("correct") != -1) { // we are correct
        correct = true;
    }
    // console.log(question);
    // console.log(correct);
    // console.log("");

    let q = question.replace(/\r?\n\.`\. */g, "\n\\u2234\t").replace(/\r?\n +/g, "\n\t").replace(/  +/g, "\t");

    let ret1 = {
        question: `<p>Is this valid?</p><pre style='tab-size:4;'>${q}</pre>`,
        answers: [
            {
                answer: "True",
                correct: correct
            },
            {
                answer: "False",
                correct: !correct
            }
        ]
    }
    ret.lessons[0].staticQuestions.push(ret1);
}

console.log(JSON.stringify(ret, null, 2).replace(/\\\\/g, "\\"));