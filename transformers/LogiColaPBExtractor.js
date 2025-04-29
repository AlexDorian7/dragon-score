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
    return waitForMilliseconds(100);
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


const ret = [];

for (let i = 0; i < 100; i++) {
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
    let type = "DISAGREEMENT";
    let correct = false;
    const question = blocks[1];
    if (blocks[2] == "") { // For some reason the Disagreeement method uses a different solution format than the rest...
        if (blocks[7].indexOf("Correct") != -1) { // we are correct
            correct = true;
        }
    } else {
        const lines = blocks[2].split("\r\n");
        if (lines[0].indexOf("correct") != -1) { // we are correct
            correct = true;
        }
        type = "ANALOGY";
        if (lines[0].indexOf("agreement") != -1) type = "AGREEMENT";
        else if (lines[0].indexOf("variation") != -1) type = "VARIATION";
        else if (lines[0].indexOf("sample-projection") != -1) type = "SAMPLE_PROJECTION";
        else if (lines[0].indexOf("difference") != -1) type = "DIFFERENCE";
        else if (lines[0].indexOf("statistical") != -1) type = "STATISTICAL";
    }
    console.warn(i);

    let q = question.replace(/\r?\n\.`\. */g, "\n\\u2234\t").replace(/\r?\n +/g, "\n\t").replace(/  +/g, "\t").replace(/\r\n/g, "\n");

    let ret1 = {
        question: q,
        type: type,
        correct: correct
    }
    ret.push(ret1);
}

console.log(JSON.stringify(ret, null, 2).replace(/\\\\/g, "\\"));