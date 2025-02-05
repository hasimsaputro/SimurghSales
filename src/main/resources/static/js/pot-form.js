(()=>{

    document.addEventListener('DOMContentLoaded', function(){

        let mainUrl = "http://localhost:8082/api";

        let bodiTabelProduk = document.querySelector(".cabang-list tbody");
        let idPot = document.querySelector("#id").value;



        let searchCabangPot = document.querySelector('input#search.searchCabangPot');
        let suggestionsCabangPotContainer = document.querySelector('.search-container .suggestions.cabangPot');
        let selectCabangPotFilter = document.querySelector('.pot-filter select');

        fetch(`${mainUrl}/cabang/getFilterItems`)
                .then(response => response.json())
                .then(data => {
                    selectCabangPotFilter.innerHTML = "";
                    let defaultOption = document.createElement("option");
                    defaultOption.value = "";
                    defaultOption.textContent = "-- Pilih Item --";
                    selectCabangPotFilter.appendChild(defaultOption);
                    data.forEach(item => {
                        let option = document.createElement("option");
                        option.value = item.value;
                        option.textContent = item.text;
                        selectCabangPotFilter.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });

        let searchCabangValue = "";
        let filterCabangValue = "";
        selectCabangPotFilter.addEventListener("change", function() {
            filterCabangValue = selectCabangPotFilter.value;
        });
        console.log(selectCabangPotFilter.value);
        if(searchCabangPot){
          searchCabangPot.addEventListener('input', function() {
              const query = searchCabangPot.value.toLowerCase();
              const filterValue = selectCabangPotFilter.value;
              suggestionsCabangPotContainer.innerHTML = '';
              fetch(`${mainUrl}/pot/getSearchCabangItems=${filterCabangValue}`)
                      .then(response => response.json())
                      .then(data => {
                          const items = data.map(option => option.text);
                          if (query) {
                              const filteredData = items.filter(item => item.toLowerCase().includes(query));
                              filteredData.forEach(item => {
                                  const div = document.createElement('div');
                                  div.classList.add('suggestion-item');
                                  div.textContent = item;

                                  div.addEventListener('click', function() {
                                      searchCabangPot.value = item;
                                      searchCabangValue = item;
                                      suggestionsCabangPotContainer.innerHTML = '';
                                  });

                                  suggestionsCabangPotContainer.appendChild(div);
                                      });
                                  }
                              })
                              .catch(error => {
                                  console.error('Error fetching data:', error);
                              });
          });
        }
        let checkedCabang = [];
        if(idPot){
            fetch(`${mainUrl}/pot/cabang/${idPot}`)
                .then(response => response.json())
                .then(data => {
                    console.log("Data dari API:", data); // Cek apakah ini array atau objek

                    if (Array.isArray(data)) { // Pastikan data adalah array
                        checkedCabang = data.map(option => +option.id);
                    } else {
                        checkedCabang = []; // Jika bukan array, kosongkan
                    }

                    console.log("Checked Cabang (ID saja):", checkedCabang);
                })
                .catch(error => console.error("Fetch error:", error));
        }

        let searchBtn = document.querySelector('.filter-button');
        if(searchBtn){
            searchBtn.addEventListener('click',()=>{
            console.log('haloo')
                fetch(`${mainUrl}/cabang/filter=${filterCabangValue}/search=${searchCabangValue}`).then(response => response.json()).then(data => {
                    bodiTabelProduk.innerHTML = "";
                    for (const cabang of data) {
                        let row = document.createElement("tr");
                        let columnCheckbox = document.createElement("td");
                        let columnKode = document.createElement("td");
                        let columnName = document.createElement("td");
                        let columnTipe = document.createElement("td");
                        let inputCheckbox = document.createElement("input");
                        inputCheckbox.setAttribute("type", "checkbox");
                        inputCheckbox.name = "cabang";
                        inputCheckbox.id = cabang.kodeCabang;

                        // Mengecek apakah produk ada di dalam array produkChecked
                        if (checkedCabang.includes(cabang.kodeCabang)) {
                            inputCheckbox.checked = true; // Menandai checkbox sebagai tercentang jika ID ada di produkChecked
                        }

                        columnKode.innerHTML = cabang.kodeCabang;
                        columnName.innerHTML = cabang.namaCabang;
                        columnTipe.innerHTML = cabang.tipeStruktur;
                        columnCheckbox.appendChild(inputCheckbox);
                        row.appendChild(columnCheckbox);
                        row.appendChild(columnKode);
                        row.appendChild(columnName);
                        row.appendChild(columnTipe);
                        bodiTabelProduk.appendChild(row);
                    }
                })
            })
        }

        setTimeout(() => {
            fetch(`${mainUrl}/cabang`).then(response => response.json()).then(data => {
                for (const cabang of data) {
                    let row = document.createElement("tr");
                    let columnCheckbox = document.createElement("td");
                    let columnKode = document.createElement("td");
                    let columnName = document.createElement("td");
                    let columnTipe = document.createElement("td");
                    let inputCheckbox = document.createElement("input");
                    inputCheckbox.setAttribute("type", "checkbox");
                    inputCheckbox.name = "cabang";
                    inputCheckbox.id = cabang.kodeCabang;
                    // Mengecek apakah produk ada di dalam array produkChecked
                    if (checkedCabang.includes(cabang.kodeCabang)) {
                        inputCheckbox.checked = true; // Menandai checkbox sebagai tercentang jika ID ada di produkChecked
                    }
    
                    columnKode.innerHTML = cabang.kodeCabang;
                    columnName.innerHTML = cabang.namaCabang;
                    columnTipe.innerHTML = cabang.tipeStruktur;
                    columnCheckbox.appendChild(inputCheckbox);
                    row.appendChild(columnCheckbox);
                    row.appendChild(columnKode);
                    row.appendChild(columnName);
                    row.appendChild(columnTipe);
                    bodiTabelProduk.appendChild(row);
                }
            })
        }, 1000)




        let saveButton = document.querySelector(".submit-button");
        saveButton.addEventListener("click", () => {
            // Get form values
            let idPot = document.querySelector("#id").value;
            let namaPot = document.querySelector("#namaPot").value;
            let idProduk = document.querySelector("#idProdukPot").value;
            let idKriteriaPaket = document.querySelector("#idKriteriaPaketPot").value;
            let tanggalMulai = document.querySelector("#tanggalMulai").value;
            let tanggalAkhir = document.querySelector("#tanggalAkhir").value;
            let pokokHutangAwal = document.querySelector("#pokokHutangAwal").value;
            let pokokHutangAkhir = document.querySelector("#pokokHutangAkhir").value;
            let idIntevalPembayaran = document.querySelector("#idIntevalPembayaranPot").value;
            let tenor = document.querySelector("#tenor").value;
            let effectiveRate = document.querySelector("#effectiveRate").value;
            let nilaiAdmin = document.querySelector("#nilaiAdmin").value;
            let nilaiProvisi = document.querySelector('#nilaiProvisi').value;
            let dp = document.querySelector('#dp').value;
            let statusMerchandise = document.querySelector('input[name="statusMerchandise"]:checked').value;
            let idKategori = document.querySelector('#idKategoriPot').value;
            let idMerk = document.querySelector('#idMerkPot').value;
            let idTipe = document.querySelector('#idTipePot').value;
            let idModel = document.querySelector('#idModelPot').value;

            // Get the selected product IDs (checkboxes)
            let cabangList = document.querySelectorAll("input[type='checkbox'][name='cabang']");
            let idCabangList = Array.from(cabangList).filter(checkbox => checkbox.checked).map(checkbox => parseInt(checkbox.id));

            console.log(idCabangList);

            // Create the request body object
            let requestBody = {
                id: parseInt(idPot),  // Convert to integer if needed
                cabangList: idCabangList,
                namaPot: namaPot,
                idProduk: parseInt(idProduk),
                idKriteriaPaket: idKriteriaPaket,
                tanggalMulai: tanggalMulai,
                tanggalAkhir: tanggalAkhir,
                pokokHutangAwal: pokokHutangAwal,
                pokokHutangAkhir: pokokHutangAkhir,
                idIntevalPembayaran: idIntevalPembayaran,
                dp: dp,
                tenor: tenor,
                effectiveRate: effectiveRate,
                nilaiAdmin: nilaiAdmin,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                nilaiProvisi: nilaiProvisi,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                statusMerchandise: statusMerchandise == 1 ? true : false,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idKategori: parseInt(idKategori),  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idMerk: idMerk,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idTipe: idTipe,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idModel: idModel,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')

            };

            // Send the request using Fetch API
            fetch(`${mainUrl}/pot`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
            })
            .then(response => response.json())
            .then(data => {
                window.location.href = "http://localhost:8082/pot";
            })
            .catch(error => {
                console.error('Error:', error);
            });
            
        })

        let produkPotInput = document.querySelector('input#namaProdukPot.namaProdukPotInput');
        let idProdukPot = document.querySelector('input#idProdukPot.idProdukPotInput');
        let suggestionsProdukPot = document.querySelector('.search-container .suggestions.produkPot');

        if(produkPotInput && suggestionsProdukPot){
            produkPotInput.addEventListener('input',()=>{
              const query = produkPotInput.value.toLowerCase();
              suggestionsProdukPot.innerHTML = '';
              fetch(`${mainUrl}/pot/getProduk`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                produkPotInput.value = item;
                                idProdukPot.value = data.find(option => option.text === item)?.value;
                                suggestionsProdukPot.innerHTML = '';
                            });

                            suggestionsProdukPot.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
          }

          let kriteriaPotInput = document.querySelector('input#namaKriteriaPaketPot.namaKriteriaPotInput');
          let idKriteriaPot = document.querySelector('input#idKriteriaPaketPot.idKriteriaPotInput');
          let suggestionsKriteriaPot = document.querySelector('.search-container .suggestions.kriteriaPot');

          if(kriteriaPotInput && suggestionsProdukPot){
              kriteriaPotInput.addEventListener('input',()=>{
                const query = kriteriaPotInput.value.toLowerCase();
                suggestionsKriteriaPot.innerHTML = '';
                fetch(`${mainUrl}/pot/getKriteria`)
                  .then(response => response.json())
                  .then(data => {
                      const items = data.map(option => option.text);
                      if (query) {
                          const filteredData = items.filter(item => item.toLowerCase().includes(query));
                          filteredData.forEach(item => {
                              const div = document.createElement('div');
                              div.classList.add('suggestion-item');
                              div.textContent = item;

                              div.addEventListener('click', function() {
                                  kriteriaPotInput.value = item;
                                  idKriteriaPot.value = data.find(option => option.text === item)?.value;
                                  suggestionsKriteriaPot.innerHTML = '';
                              });

                              suggestionsKriteriaPot.appendChild(div);
                          });
                      }
                  })
                  .catch(error => {
                      console.error('Error fetching data:', error);
                  });
                });
            }

          let intervalPotInput = document.querySelector('input#namaIntervalPot.namaIntervalPotInput');
          let idIntervalPot = document.querySelector('input#idIntevalPembayaranPot.idIntervalPotInput');
          let suggestionsIntervalPot = document.querySelector('.search-container .suggestions.intervalPot');

          if(intervalPotInput && suggestionsIntervalPot){
              intervalPotInput.addEventListener('input',()=>{
                const query = intervalPotInput.value.toLowerCase();
                suggestionsIntervalPot.innerHTML = '';
                fetch(`${mainUrl}/pot/getInterval`)
                  .then(response => response.json())
                  .then(data => {
                      const items = data.map(option => option.text);
                      if (query) {
                          const filteredData = items.filter(item => item.toLowerCase().includes(query));
                          filteredData.forEach(item => {
                              const div = document.createElement('div');
                              div.classList.add('suggestion-item');
                              div.textContent = item;

                              div.addEventListener('click', function() {
                                  intervalPotInput.value = item;
                                  idIntervalPot.value = data.find(option => option.text === item)?.value;
                                  suggestionsIntervalPot.innerHTML = '';
                              });

                              suggestionsIntervalPot.appendChild(div);
                          });
                      }
                  })
                  .catch(error => {
                      console.error('Error fetching data:', error);
                  });
                });
          }

          let kategoriPotInput = document.querySelector('input#namaKategoriPot.namaKategoriPotInput');
          let idKategoriPot = document.querySelector('input#idKategoriPot.idKategoriPotInput');
          let suggestionsKategoriPot = document.querySelector('.search-container .suggestions.kategoriPot');

          if(kategoriPotInput && suggestionsKategoriPot){
              kategoriPotInput.addEventListener('input',()=>{
                const query = kategoriPotInput.value.toLowerCase();
                suggestionsKategoriPot.innerHTML = '';
                fetch(`${mainUrl}/pot/getKategori`)
                  .then(response => response.json())
                  .then(data => {
                      const items = data.map(option => option.text);
                      if (query) {
                          const filteredData = items.filter(item => item.toLowerCase().includes(query));
                          filteredData.forEach(item => {
                              const div = document.createElement('div');
                              div.classList.add('suggestion-item');
                              div.textContent = item;

                              div.addEventListener('click', function() {
                                  kategoriPotInput.value = item;
                                  idKategoriPot.value = data.find(option => option.text === item)?.value;
                                  suggestionsKategoriPot.innerHTML = '';
                              });

                              suggestionsKategoriPot.appendChild(div);
                          });
                      }
                  })
                  .catch(error => {
                      console.error('Error fetching data:', error);
                  });
                });
          }

        let potMerkInput = document.querySelector('input#namaMerkPot.namaMerkPotInput');
        let idPotMerk = document.querySelector('input#idMerkPot.idMerkPotInput');
        let suggestionsPotMerk = document.querySelector('.search-container .suggestions.merkPot');

        if(potMerkInput && suggestionsPotMerk){
            potMerkInput.addEventListener('input',()=>{
              const query = potMerkInput.value.toLowerCase();
              suggestionsPotMerk.innerHTML = '';
              fetch(`${mainUrl}/pot/getMerk`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                potMerkInput.value = item;
                                idPotMerk.value = data.find(option => option.text === item)?.value;
                                suggestionsPotMerk.innerHTML = '';
                            });

                            suggestionsPotMerk.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
        }

        let tipePotInput = document.querySelector('input#namaTipePot.namaTipePotInput');
        let idTipePot = document.querySelector('input#idTipePot.idTipePotInput');
        let suggestionsTipePot = document.querySelector('.search-container .suggestions.tipePot');

        if(tipePotInput && suggestionsTipePot){
            tipePotInput.addEventListener('input',()=>{
              const query = tipePotInput.value.toLowerCase();
              suggestionsTipePot.innerHTML = '';
              fetch(`${mainUrl}/pot/getTipe`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                tipePotInput.value = item;
                                idTipePot.value = data.find(option => option.text === item)?.value;
                                suggestionsTipePot.innerHTML = '';
                            });

                            suggestionsTipePot.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
        }

        let modelPotInput = document.querySelector('input#namaModelPot.namaModelPotInput');
        let idModelPot = document.querySelector('input#idModelPot.idModelPotInput');
        let suggestionsModelPot = document.querySelector('.search-container .suggestions.modelPot');

        if(modelPotInput && suggestionsModelPot){
            modelPotInput.addEventListener('input',()=>{
              const query = modelPotInput.value.toLowerCase();
              suggestionsModelPot.innerHTML = '';
              fetch(`${mainUrl}/pot/getModel`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                modelPotInput.value = item;
                                idModelPot.value = data.find(option => option.text === item)?.value;
                                suggestionsModelPot.innerHTML = '';
                            });

                            suggestionsModelPot.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
        }
    })

})()