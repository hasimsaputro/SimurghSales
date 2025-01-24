(()=>{
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/mitraAgen";

        let produkInput = document.querySelector("div.search-container input.search-produk");
        let suggestionsProdukContainer = document.querySelector("div.search-container .suggestions.mitraProduk");

        let kelurahanInput = document.querySelector("div.search-container input.search-kelurahan");
        let suggestionsKelurahanContainer = document.querySelector("div.search-container .suggestions.mitraKelurahan");

        let kelurahanInput2 = document.querySelector("div.search-container2 input.search-kelurahan2");
        let suggestionsKelurahanContainer2 = document.querySelector("div.search-container2 .suggestions.mitraKelurahan2");

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

        produkInput.addEventListener("input",()=> {
            const query = produkInput.value.toLowerCase();
            suggestionsProdukContainer.innerHTML ="";
            fetch(`${mainUrl}/produk-options`).then(response => response.json()).then(data => {
            const items = data.map(option => option.namaProduk);
            if (query) {
                const filteredData = items.filter(item => item.toLowerCase().includes(query));
                console.log(filteredData)
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
            }})
            .catch(error => {
                  console.error('Error fetching data:', error);
            });
        })

        kelurahanInput.addEventListener("input",()=> {
            const query = kelurahanInput.value.toLowerCase();
            suggestionsKelurahanContainer.innerHTML ="";
            fetch(`${mainUrl}/kelurahan-options`).then(response => response.json()).then(data => {
            const items = data.map(option => option.namaKelurahan);
            if (query) {
                const filteredData = items.filter(item => item.toLowerCase().includes(query));
                console.log(filteredData)
                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item;
                    div.addEventListener('click', function() {
                        kelurahanInput.value = item;
                        suggestionsKelurahanContainer.innerHTML = '';
                    });
                    suggestionsKelurahanContainer.appendChild(div);
                });
            }})
            .catch(error => {
                    console.error('Error fetching data:', error);
                });
            })

        kelurahanInput2.addEventListener("input",()=> {
            const query = kelurahanInput2.value.toLowerCase();
            suggestionsKelurahanContainer2.innerHTML ="";
            fetch(`${mainUrl}/kelurahan-options`).then(response => response.json()).then(data => {
            const items = data.map(option => option.namaKelurahan);
            if (query) {
                const filteredData = items.filter(item => item.toLowerCase().includes(query));
                console.log(filteredData)
                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item;
                    div.addEventListener('click', function() {
                        kelurahanInput2.value = item;
                        suggestionsKelurahanContainer2.innerHTML = '';
                    });
                    suggestionsKelurahanContainer2.appendChild(div);
                });
            }})
            .catch(error => {
                console.error('Error fetching data:', error);
            });
        })

        document.addEventListener('click',function(event){
            if(!event.target.closest('.search-container')){
                suggestionsContainer.innerHTML = '';
            }
        })

        let populateUbah2 = (ubah) => {
            let bankField = document.querySelector("#bank");
            let rekField = document.querySelector("#rek");
            let namaRekField = document.querySelector("#namarekening");

            bankField.textContent = ubah.bank;
            rekField.textContent = ubah.nomorRekening;
            namaRekField.textContent = ubah.namaRekening;
        }

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