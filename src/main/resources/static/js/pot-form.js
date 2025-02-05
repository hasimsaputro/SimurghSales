(()=>{

    document.addEventListener('DOMContentLoaded', function(){
        let mainUrl = "http://localhost:8082/api";

        let bodiTabelProduk = document.querySelector(".cabang-list tbody");
        let idPot = document.querySelector("#id").value;
        let checkedCabang = [];

        fetch(`${mainUrl}/pot/cabang{idPot}`)
        .then(response => response.json())
        .then(data => {
            checkedCabang = data.map(option => option.id);
        })



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
              fetch(`${mainUrl}/pot/getSearchCabangItems=${filterValue}`)
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
            let ids = Array.from(cabangList).filter(checkbox => checkbox.checked).map(checkbox => parseInt(checkbox.id));

            // Create the request body object
            let requestBody = {
                id: parseInt(idPot),  // Convert to integer if needed
                namaPot: namaPot,
                idProduk: parseInt(idProduk),
                idKriteriaPaket: parseInt(idKriteriaPaket),
                tanggalMulai: tanggalMulai,
                tanggalAkhir: tanggalAkhir,
                pokokHutangAwal: pokokHutangAwal,
                pokokHutangAkhir: pokokHutangAkhir,
                idIntevalPembayaran: idIntevalPembayaran,
                dp: dp,
                effectiveRate: effectiveRate,
                nilaiAdmin: nilaiAdmin,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                nilaiProvisi: nilaiProvisi,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                statusMerchandise: statusMerchandise == 1 ? true : false,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idKategori: parseInt(idKategori),  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idMerk: idMerk,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idTipe: idTipe,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                idModel: idModel,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                cabangList: ids,
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

    })
     

})()