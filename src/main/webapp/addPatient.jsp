<!DOCTYPE html>
<html>
<head>
    <title>Add New Patient</title>
    <script>
        function validateForm() {
            var birthdate = new Date(document.forms["patientForm"]["BIRTHDATE"].value);
            var deathdate = new Date(document.forms["patientForm"]["DEATHDATE"].value);
            var today = new Date();
            if (birthdate > today) {
                alert("Birthdate cannot be in the future.");
                return false;
            }
            if (deathdate && birthdate && deathdate < birthdate) {
                alert("Death date cannot be before the birth date.");
                return false;
            }
            if (deathdate && deathdate > today) {
                alert("Death date cannot be in the future.");
                return false;
            }
        }

        function capitalizeFirstLetter(string) {
            return string.split(' ').map(function(word) {
                return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
            }).join(' ');
        }

        function formatInput(event) {
            const id = event.target.id.toUpperCase();
            let value = event.target.value;
            value = value.replace(/-/g, "_").replace(/[^\w\s'. _]/g, "");
            if (id === "ETHNICITY" || id === "RACE") {
                event.target.value = value.toLowerCase();
            } else {
                event.target.value = capitalizeFirstLetter(value);
            }
        }

        function generateUUID() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            document.getElementById('ID').value = generateUUID();
            document.getElementById('DRIVERS').value = 'S';
            document.getElementById('PASSPORT').value = 'X';
        });
    </script>
</head>
<body>
<h2>Add New Patient</h2>
<form name="patientForm" action="/addPatientServlet" onsubmit="return validateForm()" method="post">
    <label for="ID">ID*:</label><br>
    <input type="text" id="ID" name="ID" readonly><br>

    <label for="BIRTHDATE">Birthdate (yyyy-mm-dd)*:</label><br>
    <input type="date" id="BIRTHDATE" name="BIRTHDATE" required><br>

    <label for="DEATHDATE">Death Date (yyyy-mm-dd):</label><br>
    <input type="date" id="DEATHDATE" name="DEATHDATE"><br>

    <label for="SSN">SSN (ddd-dd-dddd)*:</label><br>
    <input type="text" id="SSN" name="SSN" pattern="\d{3}-\d{2}-\d{4}" required><br>

    <label for="DRIVERS">Driver's License (S followed by 8 digits)*:</label><br>
    <input type="text" id="DRIVERS" name="DRIVERS" pattern="S\d{8}" required maxlength="9"><br>

    <label for="PASSPORT">Passport (X followed by 8 digits and ending with X)*:</label><br>
    <input type="text" id="PASSPORT" name="PASSPORT" pattern="X\d{8}X" required maxlength="10"><br>

    <label for="PREFIX">Prefix:</label><br>
    <select id="PREFIX" name="PREFIX">
        <option value="">Select</option>
        <option value="Mr.">Mr.</option>
        <option value="Ms.">Ms.</option>
        <option value="Mrs.">Mrs.</option>
        <option value="Dr.">Dr.</option>
    </select><br>


    <label for="FIRST">First Name*:</label><br>
    <input type="text" id="FIRST" name="FIRST" required oninput="formatInput(event)"><br>

    <label for="LAST">Last Name*:</label><br>
    <input type="text" id="LAST" name="LAST" required oninput="formatInput(event)"><br>

    <label for="SUFFIX">Suffix:</label><br>
    <input type="text" id="SUFFIX" name="SUFFIX" oninput="formatInput(event)"><br>

    <label for="MAIDEN">Maiden Name:</label><br>
    <input type="text" id="MAIDEN" name="MAIDEN" oninput="formatInput(event)"><br>

    <label for="MARITAL">Marital Status:</label><br>
    <select id="MARITAL" name="MARITAL">
        <option value="">Select</option>
        <option value="M">Married</option>
        <option value="S">Single</option>
    </select><br>

    <label for="RACE">Race*:</label><br>
    <input type="text" id="RACE" name="RACE" required oninput="formatInput(event)"><br>

    <label for="ETHNICITY">Ethnicity*:</label><br>
    <input type="text" id="ETHNICITY" name="ETHNICITY" required oninput="formatInput(event)"><br>

    <label for="GENDER">Gender*:</label><br>
    <select id="GENDER" name="GENDER" required>
        <option value="">Select</option>
        <option value="M">Male</option>
        <option value="F">Female</option>
    </select><br>

    <label for="BIRTHPLACE">Birthplace*:</label><br>
    <input type="text" id="BIRTHPLACE" name="BIRTHPLACE" required oninput="formatInput(event)"><br>

    <label for="ADDRESS">Address*:</label><br>
    <input type="text" id="ADDRESS" name="ADDRESS" required oninput="formatInput(event)"><br>

    <label for="CITY">City*:</label><br>
    <input type="text" id="CITY" name="CITY" required oninput="formatInput(event)"><br>

    <label for="STATE">State*:</label><br>
    <input type="text" id="STATE" name="STATE" required oninput="formatInput(event)"><br>

    <label for="ZIP">ZIP Code*:</label><br>
    <input type="text" id="ZIP" name="ZIP" pattern="\d{5}" required><br>

    <input type="submit" value="Add Patient">
</form>
</body>
</html>
