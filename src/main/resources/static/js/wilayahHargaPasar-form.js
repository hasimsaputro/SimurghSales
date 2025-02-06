(()=>{

    document.addEventListener('DOMContentLoaded', function(){
    let mainUrl = "http://localhost:8082/api";
        let currentPage = 1;
        let filter = "";
        let search = "";
        let totalPages;
        let checkedCabang = [];
        let bodiTabelCabang = document.querySelector(".cabang-list tbody");
        let idWilayah = document.querySelector("#id").value;
        let searchCabangWilayah = document.querySelector('input#search.searchCabangWilayah');
        let suggestionsCabangWilayahContainer = document.querySelector('.search-container .suggestions.cabangWilayah');
        let selectCabangWilayahFilter = document.querySelector('.wilayah-filter select');

        fetch(`${mainUrl}/cabang/getFilterCabangWilayahItems`)
                .then(response => response.json())
                .then(data => {
                    selectCabangWilayahFilter.innerHTML = "";
                    let defaultOption = document.createElement("option");
                    defaultOption.value = "";
                    defaultOption.textContent = "-- Pilih Item --";
                    selectCabangWilayahFilter.appendChild(defaultOption);
                    data.forEach(item => {
                        let option = document.createElement("option");
                        option.value = item.value;
                        option.textContent = item.text;
                        selectCabangWilayahFilter.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });

        selectCabangWilayahFilter.addEventListener("change", function() {
            filter = selectCabangWilayahFilter.value;
        });

        if(searchCabangWilayah){
          searchCabangWilayah.addEventListener('input', function() {
              const query = searchCabangWilayah.value.toLowerCase();
              suggestionsCabangWilayahContainer.innerHTML = '';
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
                                      searchCabangWilayah.value = item;
                                      search = item;
                                      suggestionsCabangWilayahContainer.innerHTML = '';
                                  });

                                  suggestionsCabangWilayahContainer.appendChild(div);
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
            event.preventDefault();
                fetch(`${mainUrl}/cabang?page=${currentPage}&filter=${filter}&search=${search}`).then(response => response.json()).then(data => {
                    bodiTabelCabang.innerHTML = "";
                    if(data.cabangIndexDTOS && data.cabangIndexDTOS.length > 0){
                        for (const cabang of data.cabangIndexDTOS) {
                            let row = document.createElement("tr");
                            let columnCheckbox = document.createElement("td");
                            let columnKode = document.createElement("td");
                            let columnName = document.createElement("td");
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
                            columnCheckbox.appendChild(inputCheckbox);
                            row.appendChild(columnCheckbox);
                            row.appendChild(columnKode);
                            row.appendChild(columnName);
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

        let indexCabangWilayah = () => {
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
        if(idWilayah){
            fetch(`${mainUrl}/wilayah/cabang/${idWilayah}`)
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
            indexCabangWilayah();
        }, 1000)

        let leftArrows = document.querySelector("#left-arrows");
        let leftArrow = document.querySelector("#left-arrow");
        let rightArrow = document.querySelector("#right-arrow");
        let rightArrows = document.querySelector("#right-arrows");

        leftArrows.addEventListener("click", () => {
            if(currentPage > 1){
                currentPage = 1;
                indexCabangWilayah();
            }
        })

        leftArrow.addEventListener("click", () =>{
            if(currentPage > 1){
                currentPage -= 1;
                indexCabangWilayah();
            }
        })

        rightArrow.addEventListener("click", () => {
            if(currentPage < totalPages){
                currentPage += 1;
                indexCabangWilayah();
            }
        })

        rightArrows.addEventListener("click", () =>{
            if(currentPage < totalPages){
                currentPage = totalPages;
                indexCabangWilayah();
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
                    indexCabangWilayah();
                })
            }else{
                checkedCabang = [];
                checkCheckboxes();
            }
        })

        let saveButton = document.querySelector(".submit-button");
        saveButton.addEventListener("click", () => {
            // Get form values
            let idWilayah = document.querySelector("#id").value;
            let namaWilayah = document.querySelector("#namaWilayah").value;
            let idKategori = document.querySelector('#idKategoriWilayah').value;
            let status = document.querySelector('input[name="status"]:checked').value;

            // Get the selected product IDs (checkboxes)
            let cabangList = document.querySelectorAll("input[type='checkbox'][name='cabang']");
            let idCabangList = Array.from(cabangList).filter(checkbox => checkbox.checked).map(checkbox => parseInt(checkbox.id));

            console.log(idCabangList);

            // Create the request body object
            let requestBody = {
                id: idWilayah,  // Convert to integer if needed
                cabangList: idCabangList,
                namaWilayah: namaWilayah,
                status: status != 1 ? true : false,
                idKategori: parseInt(idKategori)
            };

            // Send the request using Fetch API
            fetch(`${mainUrl}/wilayah`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
            })
            .then(response => response.json())
            .then(data => {
                window.location.href = "http://localhost:8082/wilayahHargaPasar";
            })
            .catch(error => {
                console.error('Error:', error);
            });
            
        })


          let kategoriWilayahInput = document.querySelector('input#namaKategoriWilayah.namaKategoriWilayahInput');
          let idKategoriWilayah = document.querySelector('input#idKategoriWilayah.idKategoriWilayahInput');
          let suggestionsKategoriWilayah = document.querySelector('.search-container .suggestions.kategoriWilayah');

          if(kategoriWilayahInput && suggestionsKategoriWilayah){
              kategoriWilayahInput.addEventListener('input',()=>{
                const query = kategoriWilayahInput.value.toLowerCase();
                suggestionsKategoriWilayah.innerHTML = '';
                fetch(`${mainUrl}/wilayah/getKategori`)
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
                                  kategoriWilayahInput.value = item;
                                  idKategoriWilayah.value = data.find(option => option.text === item)?.value;
                                  suggestionsKategoriWilayah.innerHTML = '';
                              });

                              suggestionsKategoriWilayah.appendChild(div);
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