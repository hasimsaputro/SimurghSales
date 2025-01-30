(()=>{
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/mitraAgen";

//        let kelurahanInput = document.querySelector("div.search-container input.search-kelurahan");
//        let suggestionsKelurahanContainer = document.querySelector("div.search-container .suggestions.kelurahanIdentitas");
//        let kodeposInput = document.querySelector("input#kodeposIdentitas");
//        let kecamatanInput = document.querySelector("input#kecamatanIdentitas");
//        let kotaKabupatenInput = document.querySelector("input#kotakabupatenIdentitas");
//        let provinsiInput = document.querySelector("input#provinsiIdentitas");
//        let selectedKelurahanValue = null;
//
//        let kelurahanDomisiliInput = document.querySelector("input#kelurahanDomisili");
//        let suggestionsKelurahanDomisiliContainer = document.querySelector(".kelurahanDomisili");
//        let kodePosDomisiliInput = document.querySelector("input#kodeposDomisili");
//        let kecamatanDomisiliInput = document.querySelector("input#kecamatanDomisili");
//        let kotaKabupatenDomisiliInput = document.querySelector("input#kotakabupatenDomisili");
//        let provinsiDomisiliInput = document.querySelector("input#provinsiDomisili");
//        let selectedKelurahanDomisiliValue = null;

        // Fungsi umum untuk mengambil dan menampilkan saran
        function fetchSuggestions(query = '', target) {
            let apiUrl = "";
            if (target === "kelurahanIdentitas" || target === "kelurahanDomisili") {
                apiUrl = "kelurahan-options";  // Endpoint khusus untuk kelurahan
            } else {
                apiUrl = `${target}-options`;  // Endpoint umum untuk tipeMaster, produk, bank
            }

            fetch(`${mainUrl}/${apiUrl}`)
                .then(response => response.json())
                .then(data => {
                    let items;
                    let filteredData;

                    if (target === 'kelurahanIdentitas' || target === 'kelurahanDomisili') {
                        // Pastikan struktur data sesuai untuk kelurahan
                        items = data.map(option => ({
                            namaKelurahan: option.namaKelurahan,
                            kodePos: option.kodePos,
                            kecamatan: option.kecamatan,
                            kotaKabupaten: option.kotaKabupaten,
                            provinsi: option.provinsi
                        }));

                        // Filter data berdasarkan query yang dimasukkan
                        filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query.toLowerCase()));
                    } else {
                        // Untuk target selain kelurahan, misalnya tipeMaster, produk, bank
                        items = data.map(option => option[`nama${capitalizeFirstLetter(target)}`]);
                        filteredData = query
                            ? items.filter(item => item.toLowerCase().includes(query.toLowerCase()))
                            : items;
                    }

                    // Batasi hasil pencarian menjadi 5 item
                    const limitedResults = filteredData.slice(0, 5);
                    const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
                    suggestionsContainer.innerHTML = '';  // Hapus saran sebelumnya

                    if (limitedResults.length > 0) {
                        limitedResults.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item.namaKelurahan || item; // Jika kelurahan, tampilkan namaKelurahan

                            // Saat klik saran, isi input dengan nilai yang dipilih dan lengkapi data lainnya
                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#${target}`);
                                inputField.value = item.namaKelurahan || item;  // Isi input dengan nama kelurahan atau item lainnya

                                // Jika kelurahan, lengkapi data terkait
                                if (target === 'kelurahanIdentitas' || target === 'kelurahanDomisili') {
                                    // Menyusun bagian "Identitas" atau "Domisili" berdasarkan target
                                    const jenis = target.substring(target.indexOf('kelurahan') + 'kelurahan'.length); // Dapatkan "Identitas" atau "Domisili"

                                    const selectedItem = items.find(i => i.namaKelurahan === item.namaKelurahan);
                                    if (selectedItem) {
                                        // Isi input dengan data terkait kelurahan
                                        document.querySelector(`#kodepos${capitalizeFirstLetter(jenis)}`).value = selectedItem.kodePos;
                                        document.querySelector(`#kecamatan${capitalizeFirstLetter(jenis)}`).value = selectedItem.kecamatan;
                                        document.querySelector(`#kotakabupaten${capitalizeFirstLetter(jenis)}`).value = selectedItem.kotaKabupaten;
                                        document.querySelector(`#provinsi${capitalizeFirstLetter(jenis)}`).value = selectedItem.provinsi;
                                    }
                                }

                                suggestionsContainer.innerHTML = '';  // Hapus saran setelah dipilih
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    } else {
                        const noResultMessage = document.createElement('div');
                        noResultMessage.classList.add('suggestion-item');
                        noResultMessage.textContent = 'Tidak ada hasil ditemukan.';
                        suggestionsContainer.appendChild(noResultMessage);
                    }
                })
                .catch(error => {
                    console.error('Terjadi kesalahan saat mengambil data:', error);
                    const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
                    suggestionsContainer.innerHTML = '<div class="suggestion-item">Terjadi kesalahan saat memuat data.</div>';
                });
        }

        // Event listener untuk perubahan input (mengetik)
        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                fetchSuggestions(query, target);
            });
        });

        // Event listener untuk klik ikon pencarian
        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target'); // Mengambil target dari atribut data-target
                const query = document.querySelector(`input#${target}`).value;
                fetchSuggestions(query, target);  // Panggil fungsi untuk menampilkan saran
            });
        });

//        kelurahanInput.addEventListener("input", () => {
//            const query = kelurahanInput.value.toLowerCase();
//            suggestionsKelurahanContainer.innerHTML = "";  // Kosongkan daftar suggestion
//
//            if (query) {
//                // Jika ada input, lakukan pencarian
//                fetch(`${mainUrl}/kelurahan-options`)
//                    .then(response => response.json())
//                    .then(data => {
//                        const items = data.map(option => ({
//                            namaKelurahan: option.namaKelurahan,
//                            value: option.value,
//                            kodePos: option.kodePos,
//                            kecamatan: option.kecamatan,
//                            kotaKabupaten: option.kotaKabupaten,
//                            provinsi: option.provinsi
//                        }));
//
//                        const filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query));
//
//                        // Jika ada hasil yang cocok
//                        filteredData.forEach(item => {
//                            const div = document.createElement('div');
//                            div.classList.add('suggestion-item');
//                            div.textContent = item.namaKelurahan;
//
//                            // Event listener untuk memilih suggestion
//                            div.addEventListener('click', function() {
//                                kelurahanInput.value = item.namaKelurahan;  // Mengisi input dengan hasil yang dipilih
//                                selectedKelurahanValue = item.value;  // Menyimpan nilai kelurahan yang dipilih
//                                kodeposInput.value = item.kodePos;
//                                kecamatanInput.value = item.kecamatan;
//                                kotaKabupatenInput.value = item.kotaKabupaten;
//                                provinsiInput.value = item.provinsi;
//                                suggestionsKelurahanContainer.innerHTML = '';  // Hapus suggestions setelah klik
//                            });
//
//                            // Tambahkan item suggestion ke dalam container
//                            suggestionsKelurahanContainer.appendChild(div);
//                        });
//                    })
//                    .catch(error => {
//                        console.error('Error fetching data:', error);
//                    });
//            }
//            // Jika input kosong, pastikan tidak ada suggestion yang ditampilkan
//            else {
//                suggestionsKelurahanContainer.innerHTML = '';  // Hapus semua suggestion jika input kosong
//            }
//        });
//
//        kelurahanDomisiliInput.addEventListener("input", () => {
//            const query = kelurahanDomisiliInput.value.toLowerCase();
//            suggestionsKelurahanDomisiliContainer.innerHTML = "";  // Kosongkan daftar suggestion
//
//            if (query) {
//                // Jika ada input, lakukan pencarian
//                fetch(`${mainUrl}/kelurahan-options`)
//                    .then(response => response.json())
//                    .then(data => {
//                        const items = data.map(option => ({
//                            namaKelurahan: option.namaKelurahan,
//                            value: option.value,
//                            kodePos: option.kodePos,
//                            kecamatan: option.kecamatan,
//                            kotaKabupaten: option.kotaKabupaten,
//                            provinsi: option.provinsi
//                        }));
//
//                        const filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query));
//
//                        // Jika ada hasil yang cocok
//                        filteredData.forEach(item => {
//                            const div = document.createElement('div');
//                            div.classList.add('suggestion-item');
//                            div.textContent = item.namaKelurahan;
//
//                            // Event listener untuk memilih suggestion
//                            div.addEventListener('click', function() {
//                                kelurahanDomisiliInput.value = item.namaKelurahan;  // Mengisi input dengan hasil yang dipilih
//                                selectedKelurahanDomisiliValue = item.value;  // Menyimpan nilai kelurahan yang dipilih
//                                kodePosDomisiliInput.value = item.kodePos;
//                                kecamatanDomisiliInput.value = item.kecamatan;
//                                kotaKabupatenDomisiliInput.value = item.kotaKabupaten;
//                                provinsiDomisiliInput.value = item.provinsi;
//                                suggestionsKelurahanDomisiliContainer.innerHTML = '';  // Hapus suggestions setelah klik
//                            });
//
//                            // Tambahkan item suggestion ke dalam container
//                            suggestionsKelurahanDomisiliContainer.appendChild(div);
//                        });
//                    })
//                    .catch(error => {
//                        console.error('Error fetching data:', error);
//                    });
//            }
//            // Jika input kosong, pastikan tidak ada suggestion yang ditampilkan
//            else {
//                suggestionsKelurahanDomisiliContainer.innerHTML = '';  // Hapus semua suggestion jika input kosong
//            }
//        });

        // Fungsi untuk capitalize kata pertama (misalnya "produk" menjadi "Produk")
        function capitalizeFirstLetter(string) {
            return string.charAt(0).toUpperCase() + string.slice(1);
        }

        // Menambahkan event listener untuk klik di luar saran
        document.addEventListener('click', function(event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';  // Hapus saran jika klik di luar input atau saran
                });
            }
        });

        const alamatSamaCheckbox = document.getElementById('alamatSama');
        const address = document.getElementById('address');
        const kelurahanIdentitas = document.getElementById('kelurahanIdentitas');
        const kodeposIdentitas = document.getElementById('kodeposIdentitas');
        const kecamatanIdentitas = document.getElementById('kecamatanIdentitas');
        const kotakabupatenIdentitas = document.getElementById('kotakabupatenIdentitas');
        const provinsiIdentitas = document.getElementById('provinsiIdentitas');
        const alamatDomisili = document.getElementById('alamatDomisili');
        const kelurahanDomisili = document.getElementById('kelurahanDomisili');
        const kodeposDomisili = document.getElementById('kodeposDomisili');
        const kecamatanDomisili = document.getElementById('kecamatanDomisili');
        const kotakabupatenDomisili = document.getElementById('kotakabupatenDomisili');
        const provinsiDomisili = document.getElementById('provinsiDomisili');

        if (alamatSamaCheckbox.checked){
            alamatDomisili.readOnly = true;
            kelurahanDomisili.readOnly = true;
        }

        function copyIdentitasToDomisili() {
            if (alamatSamaCheckbox.checked) {
                alamatDomisili.value = address.value;
                kelurahanDomisili.value = kelurahanIdentitas.value;
                kodeposDomisili.value = kodeposIdentitas.value;
                kecamatanDomisili.value = kecamatanIdentitas.value;
                kotakabupatenDomisili.value = kotakabupatenIdentitas.value;
                provinsiDomisili.value = provinsiIdentitas.value;

                alamatDomisili.readOnly = true;
                kelurahanDomisili.readOnly = true;
            } else {
                alamatDomisili.value = '';
                kelurahanDomisili.value = '';
                kodeposDomisili.value = '';
                kecamatanDomisili.value = '';
                kotakabupatenDomisili.value = '';
                provinsiDomisili.value = '';

                alamatDomisili.readOnly = false;
                kelurahanDomisili.readOnly = false;
            }
        }

        alamatSamaCheckbox.addEventListener('change', copyIdentitasToDomisili);
    });
})()