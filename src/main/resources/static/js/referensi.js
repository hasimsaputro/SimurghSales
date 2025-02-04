function updateReferensiFields() {
    var selectElement = document.getElementById("identitas");
    var selectedOption = selectElement.options[selectElement.selectedIndex];

    // Ambil nilai data dari option yang dipilih
    var namaDepan = selectedOption.getAttribute("data-namaDepan");
    var namaTengah = selectedOption.getAttribute("data-namaTengah");
    var namaAkhir = selectedOption.getAttribute("data-namaAkhir");
    var referensiId = selectedOption.value;

    // Update field input
    document.getElementById("referensi-namaDepan").value = namaDepan;
    document.getElementById("referensi-namaTengah").value = namaTengah;
    document.getElementById("referensi-namaAkhir").value = namaAkhir;

    // Update input hidden referensiId
    document.querySelector(".referensiId").value = referensiId;

}
