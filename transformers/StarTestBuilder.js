// const data = `no 1 is 2;Duu13
// no 3 is 4;Xuu14
// all 1 is 2;Duu31
// all 3 is 2;Xuu32
// some 1 is 2;Duu30
// some 1 is 2;Xuu33
// 1 is 2;Dll30
// 1 is 2;Xll33
// 1 is not 2;Dll12
// 1 is not 4;Xll12
// no 1 is 2;Duu33
// no 3 is 4;Xuu30
// 1 is not 2;Dlu30
// 1 is not 4;Xlu31
// some 1 is not 2;Duu12
// some 1 is not 4;Xuu12
// 1 is not 2;Dll32
// 1 is not 4;Xll31
// 1 is not 2;Dlu12
// 1 is not 4;Xlu12
// 1 is 2;Dlu30
// 1 is 2;Xlu33
// no 1 is 2;Duu13
// no 3 is 4;Xuu13
// some 1 is 2;Duu10
// some 1 is 2;`

const data = `All 1 is 2;Duu11
No 1 is 2;Duu13
Some 1 is 2;Duu10
some 1 is not 2;Duu12
1 is 2;Dlu10
1 is not 2;Dlu12
1 is 2;Dll10
1 is not 2;Dll12
All 1 is 2;Duu31
No 1 is 2;Duu33
Some 1 is 2;Duu30
some 1 is not 2;Duu32
1 is 2;Dlu30
1 is not 2;Dlu32
1 is 2;Dll30
1 is not 2;Dll32
All 3 is 2;Xuu11
No 3 is 4;Xuu13
Some 1 is 2;Xuu10
some 1 is not 4;Xuu12
1 is 2;Xlu10
1 is not 4;Xlu12
1 is 2;Xll10
1 is not 4;Xll12
All 3 is 2;Xuu32
No 3 is 4;Xuu30
Some 1 is 2;Xuu33
some 1 is not 4;Xuu31
1 is 2;Xlu33
1 is not 4;Xlu31
1 is 2;Xll33
1 is not 4;Xll31`

const lines = data.split("\n");
let i = 0;

let array = [];

lines.forEach(line => {
    const d = line.split(";");
    const cmds = d[1];
    const form = d[0];
    let type = "starred";
    if (cmds.charAt(0) === "D")
        type = "distributed";

    let str = form.replace(/1/g, "${letter_1}").replace(/2/g, "${letter_2}").replace(/3/g, "<span style='text-decoration:underline;'>${letter_1}</span>").replace(/4/g, "<span style='text-decoration:underline;'>${letter_2}</span>");

    let obj = {
        "id": `form_${i}`,
        "fields": [
            {
                "id": "letter_1",
                "list": `${cmds.charAt(1)}_letters`
            },
            {
                "id": "letter_2",
                "list": `${cmds.charAt(2)}_letters`
            }
        ],
        "form": `<p>Which letters are ${type} here?<br/><span style='font-size:0.7em;'>Treat like letters as if they were different.</span></p><pre style='tab-size:4;'>\n\t${cmds.charAt(3) === "1" ? str : "____"}\n\t${cmds.charAt(3) === "2" ? str : "____"}\n\\u2234\t${cmds.charAt(3) === "3" ? str : "____"}</pre>`,
        "answerForms": [
            {
                "form": `Neither letter is ${type}`,
                "correct": cmds.charAt(4) === "0"
            },
            {
                "form": `Just the first letter is ${type}`,
                "correct": cmds.charAt(4) === "1"
            },
            {
                "form": `Just the second letter is ${type}`,
                "correct": cmds.charAt(4) === "2"
            },
            {
                "form": `Both letters are ${type}`,
                "correct": cmds.charAt(4) === "3"
            }
        ]
    }
    array.push(obj);
    i++;
});

console.log(JSON.stringify(array, null, 2).replace(/\\\\/g, "\\"));