(()=>{
    let mainUrl = "http://localhost:8082/api";
    let currentPage = 1;
    let filter = "";
    let search = "";
    let totalPages;
    let checkedProduk = [];

    let bodiTabelProduk = document.querySelector(".produk-list tbody");

    let searchProdukCabang = document.querySelector('input#search.searchProdukCabang');
    let suggestionsProdukCabangContainer = document.querySelector('.search-container .suggestions.produkCabang');
    let selectProdukCabangFilter = document.querySelector('.produk-filter select');

    fetch(`${mainUrl}/produk/getFilterItems`)
                .then(response => response.json())
                .then(data => {
                    selectProdukCabangFilter.innerHTML = "";
                    let defaultOption = document.createElement("option");
                    defaultOption.value = "";
                    defaultOption.textContent = "-- Pilih Item --";
                    selectProdukCabangFilter.appendChild(defaultOption);
                    data.forEach(item => {
                        let option = document.createElement("option");
                        option.value = item.value;
                        option.textContent = item.nama;
                        selectProdukCabangFilter.appendChild(option);
                        // console.log(item.nama);
                        // console.log(item.value);
                    });
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
    
                selectProdukCabangFilter.addEventListener("change", function() {
                    filter = selectProdukCabangFilter.value;
                });

                if(searchProdukCabang){
                    searchProdukCabang.addEventListener('input', function() {
                        const query = searchProdukCabang.value.toLowerCase();
                        // const filterValue = selectProdukCabangFilter.value;
                        suggestionsProdukCabangContainer.innerHTML = '';
                        fetch(`${mainUrl}/produk/getSearchItems=${filter}`)
                                .then(response => response.json())
                                .then(data => {
                                    const items = data.map(option => option.nama);
                                    if (query) {
                                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                                        filteredData.forEach(item => {
                                            const div = document.createElement('div');
                                            div.classList.add('suggestion-item');
                                            div.textContent = item;
          
                                            div.addEventListener('click', function() {
                                                searchProdukCabang.value = item;
                                                search = item;
                                                suggestionsProdukCabangContainer.innerHTML = '';
                                            });
          
                                            suggestionsProdukCabangContainer.appendChild(div);
                                                });
                                            }
                                        })
                                        .catch(error => {
                                            console.error('Error fetching data:', error);
                                        });
                    });
                  }

                  console.log(search);
                  console.log(filter);
                  
                  
                  let searchBtn = document.querySelector('.filter-button');
                  if(searchBtn){
                      searchBtn.addEventListener('click',()=>{
                        event.preventDefault;
                    //   console.log('haloo')
                          fetch(`${mainUrl}/produk?page=${currentPage}&filter=${filter}&search=${search}`)
                          .then(response => response.json())
                          .then(data => {
                            console.log(data);
                            bodiTabelProduk.innerHTML = "";
                            if(data.produkIndexDTOS && data.produkIndexDTOS.length > 0){
                                for (const produk of data.produkIndexDTOS) {
                                    let row = document.createElement("tr");
                                    let columnCheckbox = document.createElement("td");
                                    let columnKode = document.createElement("td");
                                    let columnName = document.createElement("td");
                                    // let columnValidation = document.createElement("td");
                                    let inputCheckbox = document.createElement("input");
                                    inputCheckbox.setAttribute("type", "checkbox");
                                    inputCheckbox.name = "produk";
                                    inputCheckbox.id = produk.kodeProduk;
            
                                    // Mengecek apakah produk ada di dalam array produkChecked
                                    if (checkedProduk.includes(produk.kodeProduk)) {
                                        inputCheckbox.checked = true; // Menandai checkbox sebagai tercentang jika ID ada di produkChecked
                                    }
                                    
                                      columnKode.innerHTML = produk.kodeProduk;
                                      columnName.innerHTML = produk.namaProduk;
                                      columnCheckbox.appendChild(inputCheckbox);
                                      row.appendChild(columnCheckbox);
                                      row.appendChild(columnKode);
                                      row.appendChild(columnName);  
                                    bodiTabelProduk.appendChild(row);
                                }
                            } else{
                                let row = document.createElement("tr");
                                let columnMessage = document.createElement("td");
                                columnMessage.setAttribute("colspan", "7"); // Menyesuaikan dengan jumlah kolom
                                columnMessage.textContent = "Produk tidak ditemukan atau tidak aktif"; // Menampilkan pesan
                                row.appendChild(columnMessage);
                                bodiTabelProduk.appendChild(row); // Menambahkan row dengan pesan ke dalam tabel
                            }
                              
                            currentPage = data.currentPage;
                            totalPages = data.totalPages;

                            let span = document.querySelector("#page-text");
                            span.textContent = `Page ${currentPage} of ${totalPages}`;

                            checkCheckboxes();
                          })
                          .catch(error => {
                            console.error('Error fetching data:', error);
                        });                        
                      })
                      
                  }


    let searchProduct = () => {
        
        bodiTabelProduk.innerHTML = "";
        fetch(`${mainUrl}/produk?page=${currentPage}&filter=${filter}&search=${search}`).then(response => response.json()).then(data => {
            for (const produk of data.produkIndexDTOS) {
                let row = document.createElement("tr");
                let columnCheckbox = document.createElement("td");
                let columnKode = document.createElement("td");
                let columnName = document.createElement("td");
                let inputCheckbox = document.createElement("input");
                inputCheckbox.setAttribute("type", "checkbox");
                inputCheckbox.name = "produk";
                inputCheckbox.id = produk.kodeProduk;

                columnKode.innerHTML = produk.kodeProduk;
                columnName.innerHTML = produk.namaProduk;
                columnCheckbox.appendChild(inputCheckbox);
                row.appendChild(columnCheckbox);
                row.appendChild(columnKode);
                row.appendChild(columnName);
                bodiTabelProduk.appendChild(row);
            }
            currentPage = data.currentPage;
            totalPages = data.totalPages;

            let span = document.querySelector("#page-text");
            span.textContent = `Page ${currentPage} of ${totalPages}`;
            checkCheckboxes();
        })
    }

    let checkCheckboxes = () => {
        let checkboxes = document.querySelectorAll(".produk-list tbody input[name='produk']");
        console.log(checkedProduk)
            checkboxes.forEach(checkbox => {
                if(checkedProduk.includes(parseInt(checkbox.id))){
                    checkbox.checked = true;
                } else {
                    checkbox.checked = false
                }
                checkbox.addEventListener("change", () => {
                    if(checkbox.checked){
                        checkedProduk.push(parseInt(checkbox.id));
                    }else{
                        let index = checkedProduk.indexOf(parseInt(checkbox.id));

                        // If the value exists in the array, remove it
                        if (index !== -1) {
                        checkedProduk.splice(index, 1); // Remove 1 element at the found index
                        }
                    }
                })
            })
    }

    document.addEventListener('DOMContentLoaded', function(){
        let idCabang = document.querySelector("#id").value;

        // Select the elements from the updated HTML structure
        let kelurahanInput = document.querySelector("input#kelurahan");  
        let suggestionsKelurahanContainer = document.querySelector("div.search-container .suggestions.kelurahanCabang");  
        let kodeposInput = document.querySelector("input#kodePos");  
        let kecamatanInput = document.querySelector("input#kecamatan");
        let kotaKabupatenInput = document.querySelector("input#kotakabupaten");
        let provinsiInput = document.querySelector("input#provinsi");  
        let selectedKelurahanValue = null;


        //for kelurahan input option
        kelurahanInput.addEventListener("input", () => {
            const query = kelurahanInput.value.toLowerCase();
            suggestionsKelurahanContainer.innerHTML = "";  // Clear the suggestions container

            if (query) {
                // If there's input, make a fetch request to the API
                fetch(`${mainUrl}/cabang/kelurahan-options`)  // Replace this URL with the correct one
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

                        // Filter the data to show suggestions that match the input
                        const filteredData = items.filter(item => item.namaKelurahan.toLowerCase().includes(query));

                        // If there are matches, create and display suggestion items
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item.namaKelurahan;

                            // Event listener for selecting a suggestion
                            div.addEventListener('click', function() {
                                kelurahanInput.value = item.namaKelurahan;  // Fill the input with the selected kelurahan
                                selectedKelurahanValue = item.value;  // Store the selected kelurahan value
                                kodeposInput.value = item.kodePos;  // Fill in the other fields
                                kecamatanInput.value = item.kecamatan;
                                kotaKabupatenInput.value = item.kotaKabupaten;
                                provinsiInput.value = item.provinsi;

                                // Clear suggestions after selection
                                suggestionsKelurahanContainer.innerHTML = '';
                            });

                            // Append the suggestion to the suggestions container
                            suggestionsKelurahanContainer.appendChild(div);
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching data:', error);
                    });
            } else {
                // If the input is empty, make sure no suggestions are shown
                suggestionsKelurahanContainer.innerHTML = '';
            }
        });


        if(idCabang){
            fetch(`${mainUrl}/cabang/produk/${idCabang}`)
            .then(response => response.json())
            .then(data => {
                checkedProduk = data.map(option => option.id);
            })
        }

        setTimeout(() => {
            searchProduct();
        }, 1000)


        
        let leftArrows = document.querySelector("#left-arrows");
        let leftArrow = document.querySelector("#left-arrow");
        let rightArrow = document.querySelector("#right-arrow");
        let rightArrows = document.querySelector("#right-arrows");

        leftArrows.addEventListener("click", () => {
            if(currentPage > 1){
                currentPage = 1;
                searchProduct();
            }
        })

        leftArrow.addEventListener("click", () =>{
            if(currentPage > 1){
                currentPage -= 1;
                searchProduct();
            }
        })

        rightArrow.addEventListener("click", () => {
            if(currentPage < totalPages){
                currentPage += 1;
                searchProduct();
            }
        })

        rightArrows.addEventListener("click", () =>{
            if(currentPage < totalPages){
                currentPage = totalPages;
                searchProduct();
            }
        })

        let selectAllCheckbox = document.querySelector("#select-all");
        selectAllCheckbox.addEventListener("change", () => {
            if(selectAllCheckbox.checked){
                fetch(`${mainUrl}/produk/produks`).then(response => response.json()).then(data => {
                    checkedProduk = []
                    console.log(data)
                    data.forEach(produk => {
                        checkedProduk.push(produk.kodeProduk)
                    })
                    console.log(checkedProduk);
                    searchProduct();
                })
            }else{
                checkedProduk = [];
                checkCheckboxes();
            }
        })


        let saveButton = document.querySelector(".submit-button");
        saveButton.addEventListener("click", (event) => {
            event.preventDefault(); 
            // Get form values
            let idCabang = document.querySelector("#id").value;
            let namaCabang = document.querySelector("#namaCabang").value;
            let tipeStruktur = document.querySelector("#tipeStruktur").value;
            let alamat = document.querySelector("#address").value;
            let kodePos = document.querySelector("#kodePos").value;
            let kelurahan = document.querySelector("#kelurahan").value;
            let kecamatan = document.querySelector("#kecamatan").value;
            let kotaKabupaten = document.querySelector("#kotakabupaten").value;
            let provinsi = document.querySelector("#provinsi").value;
            let nomorTelepon = document.querySelector("#nomortelepon").value;
            let nomorNpwp = document.querySelector("#NPWP").value;
            // Assuming you want to get the date value as well
            let tanggalBerdiri = document.querySelector("#tanggalBerdiri").value;  // Assuming an input with id 'tanggalBerdiri'

            // Get the status (checkbox or select value)
            let status = document.querySelector('input[name="status"]:checked').value;

            // Get the selected product IDs (checkboxes)
            let ids = checkedProduk // Convert to integer IDs

            // Create the request body object
            let requestBody = {
                id: parseInt(idCabang),  // Convert to integer if needed
                namaCabang: namaCabang,
                tipeStruktur: tipeStruktur,
                alamat: alamat,
                kodePos: kodePos,
                kelurahan: kelurahan,
                kecamatan: kecamatan,
                kotaKabupaten: kotaKabupaten,
                provinsi: provinsi,
                nomorTelepon: nomorTelepon,
                npwp: nomorNpwp,
                tanggalBerdiri: tanggalBerdiri,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                produkList: ids,
                status: status == 1 ? true : false
            };

            // Send the request using Fetch API
            fetch(`${mainUrl}/cabang`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
            })
            .then(response => response.json())
            .then(data => {
                window.location.href = "http://localhost:8082/cabang";
            })
            .catch(error => {
                console.error('Error:', error);
            });
            
        })

    })
     

})()