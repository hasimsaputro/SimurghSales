(()=>{
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/mitraAgen";

        let produkInput = document.querySelector("div.search-container input.search-produk");
        let suggestionsProdukContainer = document.querySelector("div.search-container .suggestions.mitraProduk");

    let kelurahanInput = document.querySelector("div.search-container input.search-kelurahan");
    let suggestionsKelurahanContainer = document.querySelector("div.search-container .suggestions.mitraKelurahan");
    let kodeposInput = document.querySelector("input#kodeposIdentitas");
    let kecamatanInput = document.querySelector("input#kecamatanIdentitas");
    let kotaKabupatenInput = document.querySelector("input#kotakabupatenIdentitas");
    let provinsiInput = document.querySelector("input#provinsiIdentitas");
    let selectedKelurahanValue = null;

    let kelurahanDomisiliInput = document.querySelector("input#kelurahanDomisili");
    let suggestionsKelurahanDomisiliContainer = document.querySelector(".mitraKelurahan2");
    let kodePosDomisiliInput = document.querySelector("input#kodeposDomisili");
    let kecamatanDomisiliInput = document.querySelector("input#kecamatanDomisili");
    let kotaKabupatenDomisiliInput = document.querySelector("input#kotakabupatenDomisili");
    let provinsiDomisiliInput = document.querySelector("input#provinsiDomisili");
    let selectedKelurahanDomisiliValue = null;



    let tipeMasterInput = document.querySelector("div.search-container input.search-tipeMaster");
    let suggestionsTipeMasterContainer = document.querySelector("div.search-container .suggestions.tipeMaster");

    let bankInput = document.querySelector("div.search-container input.search-bank");
    let suggestionsBankContainer = document.querySelector("div.search-container .suggestions.bank")

    tipeMasterInput.addEventListener("input",()=> {
                  const query = tipeMasterInput.value.toLowerCase();
                  suggestionsTipeMasterContainer.innerHTML ="";
                  fetch(`${mainUrl}/tipeMaster-options`).then(response => response.json()).then(data => {
                  const items = data.map(option => option.namaTipeMaster);
                  if (query) {
                                      const filteredData = items.filter(item => item.toLowerCase().includes(query));
                                      console.log(filteredData)
                                      filteredData.forEach(item => {
                                          const div = document.createElement('div');
                                          div.classList.add('suggestion-item');
                                          div.textContent = item;

                                          div.addEventListener('click', function() {
                                              tipeMasterInput.value = item;
                                              suggestionsTipeMasterContainer.innerHTML = '';
                                          });

                                          suggestionsTipeMasterContainer.appendChild(div);
                                      });
                                  }
                  })
                  .catch(error => {
                                  console.error('Error fetching data:', error);
                              });
            })

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
                          }
          })
          .catch(error => {
                          console.error('Error fetching data:', error);
                      });
    })

    bankInput.addEventListener("input",()=> {
              const query = bankInput.value.toLowerCase();
              suggestionsBankContainer.innerHTML ="";
              fetch(`${mainUrl}/bank-options`).then(response => response.json()).then(data => {
              const items = data.map(option => option.namaBank);
              if (query) {
                                  const filteredData = items.filter(item => item.toLowerCase().includes(query));
                                  console.log(filteredData)
                                  filteredData.forEach(item => {
                                      const div = document.createElement('div');
                                      div.classList.add('suggestion-item');
                                      div.textContent = item;

                                      div.addEventListener('click', function() {
                                          bankInput.value = item;
                                          suggestionsBankContainer.innerHTML = '';
                                      });

                                      suggestionsBankContainer.appendChild(div);
                                  });
                              }
              })
              .catch(error => {
                              console.error('Error fetching data:', error);
                          });
        })

kelurahanInput.addEventListener("input", () => {
    const query = kelurahanInput.value.toLowerCase();
    suggestionsKelurahanContainer.innerHTML = "";  // Kosongkan daftar suggestion

    if (query) {
        // Jika ada input, lakukan pencarian
        fetch(`${mainUrl}/kelurahan-options`)
            .then(response => response.json())
            .then(data => {
                const items = data.map(option => ({
                    namaKelurahan: option.namaKelurahan,
                    value: option.value,
                    kodePos: option.kodePos,
                    kecamatan: option.kecamatan,
                    kotaKabupaten: option.kotaKabupaten,
                    provinsi: option.provinsi
                }));

                const filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query));

                // Jika ada hasil yang cocok
                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item.namaKelurahan;

                    // Event listener untuk memilih suggestion
                    div.addEventListener('click', function() {
                        kelurahanInput.value = item.namaKelurahan;  // Mengisi input dengan hasil yang dipilih
                        selectedKelurahanValue = item.value;  // Menyimpan nilai kelurahan yang dipilih
                        kodeposInput.value = item.kodePos;
                        kecamatanInput.value = item.kecamatan;
                        kotaKabupatenInput.value = item.kotaKabupaten;
                        provinsiInput.value = item.provinsi;
                        suggestionsKelurahanContainer.innerHTML = '';  // Hapus suggestions setelah klik
                    });

                    // Tambahkan item suggestion ke dalam container
                    suggestionsKelurahanContainer.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }
    // Jika input kosong, pastikan tidak ada suggestion yang ditampilkan
    else {
        suggestionsKelurahanContainer.innerHTML = '';  // Hapus semua suggestion jika input kosong
    }
});


kelurahanDomisiliInput.addEventListener("input", () => {
    const query = kelurahanDomisiliInput.value.toLowerCase();
    suggestionsKelurahanDomisiliContainer.innerHTML = "";  // Kosongkan daftar suggestion

    if (query) {
        // Jika ada input, lakukan pencarian
        fetch(`${mainUrl}/kelurahan-options`)
            .then(response => response.json())
            .then(data => {
                const items = data.map(option => ({
                    namaKelurahan: option.namaKelurahan,
                    value: option.value,
                    kodePos: option.kodePos,
                    kecamatan: option.kecamatan,
                    kotaKabupaten: option.kotaKabupaten,
                    provinsi: option.provinsi
                }));

                const filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query));

                // Jika ada hasil yang cocok
                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item.namaKelurahan;

                    // Event listener untuk memilih suggestion
                    div.addEventListener('click', function() {
                        kelurahanDomisiliInput.value = item.namaKelurahan;  // Mengisi input dengan hasil yang dipilih
                        selectedKelurahanDomisiliValue = item.value;  // Menyimpan nilai kelurahan yang dipilih
                        kodePosDomisiliInput.value = item.kodePos;
                        kecamatanDomisiliInput.value = item.kecamatan;
                        kotaKabupatenDomisiliInput.value = item.kotaKabupaten;
                        provinsiDomisiliInput.value = item.provinsi;
                        suggestionsKelurahanDomisiliContainer.innerHTML = '';  // Hapus suggestions setelah klik
                    });

                    // Tambahkan item suggestion ke dalam container
                    suggestionsKelurahanDomisiliContainer.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }
    // Jika input kosong, pastikan tidak ada suggestion yang ditampilkan
    else {
        suggestionsKelurahanDomisiliContainer.innerHTML = '';  // Hapus semua suggestion jika input kosong
    }
});


//
//kelurahanInput2.addEventListener("input", () => {
//        const query = kelurahanInput2.value.toLowerCase();
//        suggestionsKelurahanContainer2.innerHTML = "";  // Kosongkan daftar suggestion
//
//        if (query) {
//            // Jika ada input, lakukan pencarian
//            fetch(`${mainUrl}/kelurahan-options`)
//                .then(response => response.json())
//                .then(data => {
//                    const items = data.map(option => option.namaKelurahan);
//                    const filteredData = items.filter(item => item.toLowerCase().includes(query));
//
//                    // Jika ada hasil yang cocok
//                    filteredData.forEach(item => {
//                        const div = document.createElement('div');
//                        div.classList.add('suggestion-item');
//                        div.textContent = item;
//
//                        // Event listener untuk memilih suggestion
//                        div.addEventListener('click', function() {
//                            kelurahanInput2.value = item;  // Mengisi input dengan hasil yang dipilih
//                            suggestionsKelurahanContainer2.innerHTML = '';  // Hapus suggestions setelah klik
//                        });
//
//                        // Tambahkan item suggestion ke dalam container
//                        suggestionsKelurahanContainer2.appendChild(div);
//                    });
//                })
//                .catch(error => {
//                    console.error('Error fetching data:', error);
//                });
//        }
//        // Jika input kosong, pastikan tidak ada suggestion yang ditampilkan
//        else {
//            suggestionsKelurahanContainer2.innerHTML = '';  // Hapus semua suggestion jika input kosong
//        }
//    });

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