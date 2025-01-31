let mainUrl = "http://localhost:8082/api/dataLeads";

// Produk Input
let produkInput = document.querySelector("input#product.search-produk");
let suggestionsProdukContainer = document.querySelector(".suggestions-produk");
let produkName = "";
let dataDTO = {

}
let sumberDataAplikasi = [];
produkInput.addEventListener("input", () => {
    const query = produkInput.value.toLowerCase();
    suggestionsProdukContainer.innerHTML = "";
    fetch(`${mainUrl}/produk`).then(response => response.json()).then(data => {
        const items = data.map(option => option.text);
        if (query) {
            const filteredData = items.filter(item => item.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item;
                div.addEventListener('click', function() {
                    produkInput.value = item;
                    suggestionsProdukContainer.innerHTML = '';
                });
                suggestionsProdukContainer.appendChild(div);
            });
        }
    }).catch(error => console.error('Error fetching data:', error));
});

// Tipe Aplikasi Input
let tipeAplikasiInput = document.querySelector("input#tipe-aplikasi");
let suggestionsTipeAplikasiContainer = document.querySelector(".suggestions-tipeAplikasi");
tipeAplikasiInput.addEventListener("input", () => {
    const query = tipeAplikasiInput.value.toLowerCase();
    suggestionsTipeAplikasiContainer.innerHTML = "";
    fetch(`${mainUrl}/tipeAplikasi`).then(response => response.json()).then(data => {
        const items = data.map(option => option.text);
        if (query) {
            const filteredData = items.filter(item => item.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item;
                div.addEventListener('click', function() {
                    tipeAplikasiInput.value = item;
                    suggestionsTipeAplikasiContainer.innerHTML = '';
                });
                suggestionsTipeAplikasiContainer.appendChild(div);
            });
        }
    }).catch(error => console.error('Error fetching data:', error));
});


produkInput.addEventListener("change", () => {
    setTimeout(() => {
        produkName = produkInput.value;
        dataDTO = {
            produkName: produkName,
            cabangId: cabangId
         }
        const url = new URL(`${mainUrl}/sumberDataAplikasi`);
        const searchParams = new URLSearchParams(dataDTO);
        url.search = searchParams.toString();
        fetch(url).then(response => response.json()).then(data => {
            const items = data.map(option => ({ text: option.text, value: option.value }));
            items.forEach(item => {
                sumberDataAplikasi.push(item)
            });
        }).catch(error => console.error('Error fetching data:', error));
    }, 1000)
})
 cabang = document.querySelector("#nama-cabang-tujuan");
 cabangId = cabang.value;
 let sumberDataAplikasiInput = document.querySelector("input#sumber-data-aplikasi");
 let suggestionsSumberDataAplikasiContainer = document.querySelector(".suggestions-sumberDataAplikasi");
 let selectedSumberDataAplikasiValue = null;

 sumberDataAplikasiInput.addEventListener("focus", () => {
    let suggestionsCount = suggestionsSumberDataAplikasiContainer.querySelectorAll(".suggestion-item")
    if(suggestionsCount.length == 0){
        sumberDataAplikasi.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item.text;
                div.addEventListener('click', function() {
                sumberDataAplikasiInput.value = item.text;
                selectedSumberDataAplikasiValue = item.value;
                suggestionsSumberDataAplikasiContainer.innerHTML = '';
            });
            suggestionsSumberDataAplikasiContainer.appendChild(div);
        })
    }
 })

// Sumber Data Aplikasi Input
sumberDataAplikasiInput.addEventListener("input", () => {
    const query = sumberDataAplikasiInput.value.toLowerCase();
    items = sumberDataAplikasi
    suggestionsSumberDataAplikasiContainer.innerHTML = "";
    const filteredData = items.filter(item => item.text.toLowerCase().includes(query));
    filteredData.forEach(item => {
        const div = document.createElement('div');
        div.classList.add('suggestion-item');
        div.textContent = item.text;
        div.addEventListener('click', function() {
            sumberDataAplikasiInput.value = item.text;
            selectedSumberDataAplikasiValue = item.value;
            suggestionsSumberDataAplikasiContainer.innerHTML = '';
        });
        suggestionsSumberDataAplikasiContainer.appendChild(div);
    });
});

// Keterangan Aplikasi Input
let keteranganAplikasiInput = document.querySelector("input#keterangan-aplikasi");
let suggestionsKeteranganAplikasiContainer = document.querySelector(".suggestions-keterangan-aplikasi");
let surveyorInput = document.querySelector("input#nama-surveyor");
let selectedKeteranganAplikasiValue = null;
keteranganAplikasiInput.addEventListener("input", () => {
    const query = keteranganAplikasiInput.value.toLowerCase();
    suggestionsKeteranganAplikasiContainer.innerHTML = "";
    fetch(`${mainUrl}/keteranganAplikasi?cabangId=${cabangId}`).then(response => response.json()).then(data => {
        const items = data.map(option => ({ text: option.text, value: option.value, surveyor: option.surveyor, idSurveyor: option.idSurveyor  }));
        if (query) {
            const filteredData = items.filter(item => item.text.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item.text;
                div.addEventListener('click', function() {
                    keteranganAplikasiInput.value = item.text;
                    selectedKeteranganAplikasiValue = item.value;
                    suggestionsKeteranganAplikasiContainer.innerHTML = '';
                    surveyorInput.value = item.surveyor
                });
                suggestionsKeteranganAplikasiContainer.appendChild(div);
            });
        }
    }).catch(error => console.error('Error fetching data:', error));
});

// Referensi
let referensiInput = document.querySelector("input#referensi");
let suggestionsReferensiContainer = document.querySelector(".suggestions-referensi");
let referensiValueInput = document.querySelector("input#referensiValue")
referensiInput.addEventListener("input", () => {
    const query = referensiInput.value.toLowerCase();
    suggestionsReferensiContainer.innerHTML = "";
    fetch(`${mainUrl}/referensi`).then(response => response.json()).then(data => {
        const items = data.map(option => ({ text: option.text, value: option.value }));
        if (query) {
            const filteredData = items.filter(item => item.text.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item.text;
                div.addEventListener('click', function() {
                    referensiInput.value = item.text;
                    referensiValueInput.value = item.value;
                    suggestionsReferensiContainer.innerHTML = '';
                });
                suggestionsReferensiContainer.appendChild(div);
            });
        }
    }).catch(error => console.error('Error fetching data:', error));
});

// Kelurahan Input (Identitas)
let kelurahanInput = document.querySelector("input#kelurahan");
let suggestionsKelurahanContainer = document.querySelector(".suggestions-kelurahan");
let kodeposInput = document.querySelector("input#kode-pos");
let kecamatanInput = document.querySelector("input#kecamatan");
let kotaKabupatenInput = document.querySelector("input#kota");
let provinsiInput = document.querySelector("input#provinsi");
let selectedKelurahanValue = null;

kelurahanInput.addEventListener("input", () => {
    const query = kelurahanInput.value.toLowerCase();
    suggestionsKelurahanContainer.innerHTML = "";
    fetch(`${mainUrl}/kelurahan`).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        const items = data.map(option => ({
            text: option.kelurahan,
            value: option.value,
            kodePos: option.kodePos,
            kecamatan: option.kecamatan,
            kotaKabupaten: option.kotaKabupaten,
            provinsi: option.provinsi
        }));
        if (query) {
            const filteredData = items.filter(item => item.text.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item.text;
                div.addEventListener('click', function() {
                    kelurahanInput.value = item.text;
                    selectedKelurahanValue = item.value;
                    kodeposInput.value = item.kodePos;
                    kecamatanInput.value = item.kecamatan;
                    kotaKabupatenInput.value = item.kotaKabupaten;
                    provinsiInput.value = item.provinsi;
                    suggestionsKelurahanContainer.innerHTML = '';
                });
                suggestionsKelurahanContainer.appendChild(div);
            });
        }
    }).catch(error => {
        console.error('Error fetching data:', error);
    });
});

// Kelurahan Domisili Input
let kelurahanDomisiliInput = document.querySelector("input#kelurahan-domisili");
let suggestionsKelurahanDomisiliContainer = document.querySelector(".suggestions-kelurahanDomisili");
let kodePosDomisiliInput = document.querySelector("input#kode-pos-domisili");
let kecamatanDomisiliInput = document.querySelector("input#kecamatan-domisili");
let kotaKabupatenDomisiliInput = document.querySelector("input#kota-domisili");
let provinsiDomisiliInput = document.querySelector("input#provinsi-domisili");
let selectedKelurahanDomisiliValue = null;

kelurahanDomisiliInput.addEventListener("input", () => {
    const query = kelurahanDomisiliInput.value.toLowerCase();
    suggestionsKelurahanDomisiliContainer.innerHTML = "";
    fetch(`${mainUrl}/kelurahan`).then(response => response.json()).then(data => {
        const items = data.map(option => ({
            text: option.kelurahan,
            value: option.value,
            kodePos: option.kodePos,
            kecamatan: option.kecamatan,
            kotaKabupaten: option.kotaKabupaten,
            provinsi: option.provinsi
        }));
        if (query) {
            const filteredData = items.filter(item => item.text.toLowerCase().includes(query));
            filteredData.forEach(item => {
                const div = document.createElement('div');
                div.classList.add('suggestion-item');
                div.textContent = item.text;
                div.addEventListener('click', function() {
                    kelurahanDomisiliInput.value = item.text;
                    selectedKelurahanDomisiliValue = item.value;
                    kodePosDomisiliInput.value = item.kodePos;
                    kecamatanDomisiliInput.value = item.kecamatan;
                    kotaKabupatenDomisiliInput.value = item.kotaKabupaten;
                    provinsiDomisiliInput.value = item.provinsi;
                    suggestionsKelurahanDomisiliContainer.innerHTML = '';
                });
                suggestionsKelurahanDomisiliContainer.appendChild(div);
            });
        }
    }).catch(error => {
        console.error('Error fetching data:', error);
    });
});

// Alamat Sama Checkbox
document.addEventListener('DOMContentLoaded', function() {
    // Ambil elemen-elemen checkbox dan input yang diperlukan
    const alamatSamaCheckbox = document.querySelector('input[type="checkbox"]'); // Checkbox Sama Dengan Alamat Identitas
    const alamatDomisili = document.getElementById('alamat-domisili');
    const kelurahanDomisili = document.getElementById('kelurahan-domisili');
    const kodeposDomisili = document.getElementById('kode-pos-domisili');
    const kecamatanDomisili = document.getElementById('kecamatan-domisili');
    const kotakabupatenDomisili = document.getElementById('kota-domisili');
    const provinsiDomisili = document.getElementById('provinsi-domisili');

    const alamatIdentitas = document.getElementById('alamat');
    const kelurahanIdentitas = document.getElementById('kelurahan');
    const kodeposIdentitas = document.getElementById('kode-pos');
    const kecamatanIdentitas = document.getElementById('kecamatan');
    const kotakabupatenIdentitas = document.getElementById('kota');
    const provinsiIdentitas = document.getElementById('provinsi');

    // Fungsi untuk menyalin data alamat identitas ke alamat domisili dan menonaktifkan input jika alamat sama
    function copyIdentitasToDomisili() {
        if (alamatSamaCheckbox.checked) {
            // Salin nilai dari alamat identitas ke alamat domisili
            alamatDomisili.value = alamatIdentitas.value;
            kelurahanDomisili.value = kelurahanIdentitas.value;
            kodeposDomisili.value = kodeposIdentitas.value;
            kecamatanDomisili.value = kecamatanIdentitas.value;
            kotakabupatenDomisili.value = kotakabupatenIdentitas.value;
            provinsiDomisili.value = provinsiIdentitas.value;

            // Set readonly pada input alamat domisili dan kelurahan domisili
            alamatDomisili.readOnly = true;
            kelurahanDomisili.readOnly = true;
            kodeposDomisili.readOnly = true;
            kecamatanDomisili.readOnly = true;
            kotakabupatenDomisili.readOnly = true;
            provinsiDomisili.readOnly = true;
        } else {
            // Kosongkan nilai jika checkbox tidak dicentang
            alamatDomisili.value = '';
            kelurahanDomisili.value = '';
            kodeposDomisili.value = '';
            kecamatanDomisili.value = '';
            kotakabupatenDomisili.value = '';
            provinsiDomisili.value = '';

            // Set readonly menjadi false pada input alamat domisili dan kelurahan domisili
            alamatDomisili.readOnly = false;
            kelurahanDomisili.readOnly = false;
            kodeposDomisili.readOnly = false;
            kecamatanDomisili.readOnly = false;
            kotakabupatenDomisili.readOnly = false;
            provinsiDomisili.readOnly = false;
        }
    }

    // Tambahkan event listener untuk perubahan pada checkbox 'alamatSama'
    alamatSamaCheckbox.addEventListener('change', copyIdentitasToDomisili);
});
