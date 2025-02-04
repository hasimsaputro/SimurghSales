(()=>{
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/mitraAgen";

        let currentPage = 0;
        let isLoading = false;

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

        function fetchSuggestions(query = '', target) {
            if (isLoading) return;
            isLoading = true;

            let apiUrl = "";
            if (target === "kelurahanIdentitas" || target === "kelurahanDomisili") {
                apiUrl = "kelurahan-options";
            } else {
                apiUrl = `${target}-options`;
            }

            fetch(`${mainUrl}/${apiUrl}?page=${currentPage}&size=5`)
                .then(response => response.json())
                .then(data => {
                    isLoading = false;
                    let items = [];
                    let filteredData = [];

                    if (target === 'kelurahanIdentitas' || target === 'kelurahanDomisili') {
                        items = data.map(option => ({
                            namaKelurahan: option.namaKelurahan,
                            kodePos: option.kodePos,
                            kecamatan: option.kecamatan,
                            kotaKabupaten: option.kotaKabupaten,
                            provinsi: option.provinsi
                        }));

                        filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query.toLowerCase()));
                    } else {
                        items = data.map(option => option[`nama${capitalizeFirstLetter(target)}`]);
                        filteredData = query
                            ? items.filter(item => item.toLowerCase().includes(query.toLowerCase()))
                            : items;
                    }

                    const limitedResults = filteredData.slice(0, 5);
                    const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
                    suggestionsContainer.innerHTML = '';

                    if (limitedResults.length > 0) {
                        limitedResults.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item.namaKelurahan || item;

                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#${target}`);
                                inputField.value = item.namaKelurahan || item;

                                if (target === 'kelurahanIdentitas' || target === 'kelurahanDomisili') {
                                    const jenis = target.substring(target.indexOf('kelurahan') + 'kelurahan'.length); // Ambil "Identitas" atau "Domisili"
                                    const selectedItem = items.find(i => i.namaKelurahan === item.namaKelurahan);
                                    if (selectedItem) {
                                        document.querySelector(`#kodepos${capitalizeFirstLetter(jenis)}`).value = selectedItem.kodePos;
                                        document.querySelector(`#kecamatan${capitalizeFirstLetter(jenis)}`).value = selectedItem.kecamatan;
                                        document.querySelector(`#kotakabupaten${capitalizeFirstLetter(jenis)}`).value = selectedItem.kotaKabupaten;
                                        document.querySelector(`#provinsi${capitalizeFirstLetter(jenis)}`).value = selectedItem.provinsi;
                                    }

                                    if (alamatSamaCheckbox.checked) {
                                        alamatDomisili.value = address.value;
                                        kelurahanDomisili.value = kelurahanIdentitas.value;
                                        kodeposDomisili.value = kodeposIdentitas.value;
                                        kecamatanDomisili.value = kecamatanIdentitas.value;
                                        kotakabupatenDomisili.value = kotakabupatenIdentitas.value;
                                        provinsiDomisili.value = provinsiIdentitas.value;
                                    }
                                }
                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });

//                        if (filteredData.length > currentPage * 5) {
//                            const loadMoreButton = document.createElement('div');
//                            loadMoreButton.classList.add('load-more');
//                            loadMoreButton.textContent = 'Muat Lebih Banyak';
//                            loadMoreButton.addEventListener('click', function () {
//                                currentPage++;
//                                fetchSuggestions(query, target);
//                            });
//                            suggestionsContainer.appendChild(loadMoreButton);
//                        }
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
                    isLoading = false;
                });
        }

        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                currentPage = 0;
                fetchSuggestions(query, target);
            });
        });

        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target');
                const query = document.querySelector(`input#${target}`).value;
                currentPage = 0;
                fetchSuggestions(query, target);
            });
        });

        function capitalizeFirstLetter(string) {
            return string.charAt(0).toUpperCase() + string.slice(1);
        }

        document.addEventListener('click', function(event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';
                });
            }
        });

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