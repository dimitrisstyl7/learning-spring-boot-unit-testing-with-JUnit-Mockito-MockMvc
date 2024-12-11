function showOrHideGrade(gradeType) {
    let x;
    if (gradeType === "math") {
        x = document.getElementById("mathGrade");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
    if (gradeType === "science") {
        x = document.getElementById("scienceGrade");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
    if (gradeType === "history") {
        x = document.getElementById("historyGrade");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
}

function deleteStudent(id) {
    window.location.href = "/delete/student/" + id;
}

function deleteMathGrade(id) {
    window.location.href = "/delete/grades/" + id + "/" + "math";
}

function deleteScienceGrade(id) {
    window.location.href = "/delete/grades/" + id + "/" + "science";
}

function deleteHistoryGrade(id) {
    window.location.href = "/delete/grades/" + id + "/" + "history";
}

function studentInfo(id) {
    window.location.href = "/studentInformation/" + id;
}