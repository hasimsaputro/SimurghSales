(()=>{
    let mainUrl = "http://localhost:8082/api";
    let currentPage = 1;
    let filter = "";
    let search = "";
    let totalPages;
    let checkedCabang = [];
    let bodiTabelCabang = document.querySelector(".cabang-list tbody");
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

    selectCabangPotFilter.addEventListener("change", function() {
        filter = selectCabangPotFilter.value;
    });

    if(searchCabangPot){
      searchCabangPot.addEventListener('input', function() {
          const query = searchCabangPot.value.toLowerCase();
          suggestionsCabangPotContainer.innerHTML = '';
          fetch(`${mainUrl}/cabang/getSearchCabangItems=${filter}`)
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
                                  search = item;
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

    

    let searchBtn = document.querySelector('.filter-button');
    if(searchBtn){
        searchBtn.addEventListener('click',(event)=>{
        console.log('haloo')
        event.preventDefault();
            fetch(`${mainUrl}/cabang?page=${currentPage}&filter=${filter}&search=${search}`).then(response => response.json()).then(data => {
                bodiTabelCabang.innerHTML = "";
                if(data.cabangIndexDTOS && data.cabangIndexDTOS.length > 0){
                    for (const cabang of data.cabangIndexDTOS) {
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
                        bodiTabelCabang.appendChild(row);
                    }
                }else{
                    let row = document.createElement("tr");
                    let columnMessage = document.createElement("td");
                    columnMessage.setAttribute("colspan", "7"); // Menyesuaikan dengan jumlah kolom
                    columnMessage.textContent = "Cabang tidak ditemukan atau tidak aktif"; // Menampilkan pesan
                    row.appendChild(columnMessage);
                    bodiTabelCabang.appendChild(row); // Menambahkan row dengan pesan ke dalam tabel
                }
                currentPage = data.currentPage;
                totalPages = data.totalPages;

                let span = document.querySelector("#page-text");
                span.textContent = `Page ${currentPage} of ${totalPages}`;

                checkCheckboxes();
            })
        })
    }

    let indexCabangPOT = () => {
        bodiTabelCabang.innerHTML = "";
        fetch(`${mainUrl}/cabang?page=${currentPage}&filter=${filter}&search=${search}`).then(response => response.json()).then(data => {
            for (const cabang of data.cabangIndexDTOS) {
                let row = document.createElement("tr");
                let columnCheckbox = document.createElement("td");
                let columnKode = document.createElement("td");
                let columnName = document.createElement("td");
                let columnTipe = document.createElement("td");
                let inputCheckbox = document.createElement("input");
                inputCheckbox.setAttribute("type", "checkbox");
                inputCheckbox.name = "cabang";
                inputCheckbox.id = cabang.kodeCabang;

                columnKode.innerHTML = cabang.kodeCabang;
                columnName.innerHTML = cabang.namaCabang;
                columnTipe.innerHTML = cabang.tipeStruktur;
                columnCheckbox.appendChild(inputCheckbox);
                row.appendChild(columnCheckbox);
                row.appendChild(columnKode);
                row.appendChild(columnName);
                row.appendChild(columnTipe);
                bodiTabelCabang.appendChild(row);
            }
            currentPage = data.currentPage;
            totalPages = data.totalPages;

            let span = document.querySelector("#page-text");
            span.textContent = `Page ${currentPage} of ${totalPages}`;

                checkCheckboxes();
        })
    }

    // setTimeout(() => {
    //     fetch(`${mainUrl}/cabang`).then(response => response.json()).then(data => {
    //         for (const cabang of data) {
    //             let row = document.createElement("tr");
    //             let columnCheckbox = document.createElement("td");
    //             let columnKode = document.createElement("td");
    //             let columnName = document.createElement("td");
    //             let columnTipe = document.createElement("td");
    //             let inputCheckbox = document.createElement("input");
    //             inputCheckbox.setAttribute("type", "checkbox");
    //             inputCheckbox.name = "cabang";
    //             inputCheckbox.id = cabang.kodeCabang;
    //             // Mengecek apakah produk ada di dalam array produkChecked
    //             if (checkedCabang.includes(cabang.kodeCabang)) {
    //                 inputCheckbox.checked = true; // Menandai checkbox sebagai tercentang jika ID ada di produkChecked
    //             }

    //             columnKode.innerHTML = cabang.kodeCabang;
    //             columnName.innerHTML = cabang.namaCabang;
    //             columnTipe.innerHTML = cabang.tipeStruktur;
    //             columnCheckbox.appendChild(inputCheckbox);
    //             row.appendChild(columnCheckbox);
    //             row.appendChild(columnKode);
    //             row.appendChild(columnName);
    //             row.appendChild(columnTipe);
    //             bodiTabelCabang.appendChild(row);
    //         }
    //     })
    // }, 1000)

    let checkCheckboxes = () => {
        let checkboxes = document.querySelectorAll(".cabang-list tbody input[name='cabang']");
        console.log(checkedCabang)
            checkboxes.forEach(checkbox => {
                if(checkedCabang.includes(parseInt(checkbox.id))){
                    checkbox.checked = true;
                } else {
                    checkbox.checked = false
                }
                checkbox.addEventListener("change", () => {
                    if(checkbox.checked){
                        checkedCabang.push(parseInt(checkbox.id));
                    }else{
                        let index = checkedCabang.indexOf(parseInt(checkbox.id));

                        // If the value exists in the array, remove it
                        if (index !== -1) {
                            checkedCabang.splice(index, 1); // Remove 1 element at the found index
                        }
                    }
                })
            })
    }
    document.addEventListener('DOMContentLoaded', function(){
        if(idPot){
            fetch(`${mainUrl}/pot/cabang/${idPot}`)
                .then(response => response.json())
                .then(data => {
                    if (Array.isArray(data)) { 
                        checkedCabang = data.map(option => +option.id);
                    } else {
                        checkedCabang = []; 
                    }
                })
                .catch(error => console.error("Fetch error:", error));
        }

        setTimeout(() => {
            indexCabangPOT();
        }, 1000)

        let leftArrows = document.querySelector("#left-arrows");
        let leftArrow = document.querySelector("#left-arrow");
        let rightArrow = document.querySelector("#right-arrow");
        let rightArrows = document.querySelector("#right-arrows");

        leftArrows.addEventListener("click", () => {
            if(currentPage > 1){
                currentPage = 1;
                indexCabangPOT();
            }
        })

        leftArrow.addEventListener("click", () =>{
            if(currentPage > 1){
                currentPage -= 1;
                indexCabangPOT();
            }
        })

        rightArrow.addEventListener("click", () => {
            if(currentPage < totalPages){
                currentPage += 1;
                indexCabangPOT();
            }
        })

        rightArrows.addEventListener("click", () =>{
            if(currentPage < totalPages){
                currentPage = totalPages;
                indexCabangPOT();
            }
        })

        let selectAllCheckbox = document.querySelector("#select-all");
        selectAllCheckbox.addEventListener("change", () => {
            if(selectAllCheckbox.checked){
                fetch(`${mainUrl}/cabang/cabangs`).then(response => response.json()).then(data => {
                    checkedCabang = []
                    console.log(data)
                    data.forEach(cabang => {
                        checkedCabang.push(cabang.kodeCabang)
                    })
                    console.log(checkedCabang);
                    indexCabangPOT();
                })
            }else{
                checkedCabang = [];
                checkCheckboxes();
            }
        })

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