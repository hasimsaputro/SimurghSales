(()=>{
    let mainUrl = "http://localhost:8082/api/mitraAgen";

    let produkInput = document.querySelector("div.search-container input.search-produk");
    let suggestionsProdukContainer = document.querySelector("div.search-container .suggestions.mitraProduk");

    let kelurahanInput = document.querySelector("div.search-container input.search-kelurahan");
    let suggestionsKelurahanContainer = document.querySelector("div.search-container .suggestions.mitraKelurahan");

    let kelurahanInput2 = document.querySelector("div.search-container2 input.search-kelurahan2");
    let suggestionsKelurahanContainer2 = document.querySelector("div.search-container2 .suggestions.mitraKelurahan2");


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
                              }
              })
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
                                  }
                  })
                  .catch(error => {
                                  console.error('Error fetching data:', error);
                              });
            })

    document.addEventListener('click',function(event){
        if(!event.target.closest('.search-container')){
            suggestionsContainer.innerHTML = '';
        }
    })

//    accordionData1.addEventListener("click", () => {
//        let request = new XMLHttpRequest();
//        request.open("GET", `${mainUrl}${endPointUbah}?id=${id}`);
//        request.send();
//        request.onload = () => {
//            populateUbah1(JSON.parse(request.response));
//        }
//    })
//
//    accordionData2.addEventListener("click", () => {
//        let request = new XMLHttpRequest();
//        request.open("GET", `${mainUrl}${endPointUbah}?id=${id}`);
//        request.send();
//        request.onload = () => {
//            populateDetail2(JSON.parse(request.response));
//        }
//    })
//
//    let populateUbah1 = (ubah) => {
//        let idIdentitasField = document.querySelector("#idIdentitas")
//        let identitasField = document.querySelector("#identitas");
//        let namaField = document.querySelector("#nama");
//        let kelaminPerempuanField = document.querySelector("#perempuan");
//        let kelaminLakiField = document.querySelector("#laki-laki");
//        let npwpField = document.querySelector("#NPWP");
//        let alamatField = document.querySelector("#address");
//        let kelIdenField = document.querySelector("#kelurahanIdentitas");
//        let kodPosIdenField = document.querySelector("#kodeposIdentitas");
//        let kecIdenField = document.querySelector("#kecamatanIdentitas");
//        let kabIdenField = document.querySelector("#kotakabupatenIdentitas");
//        let provIdenField = document.querySelector("#provinsiIdentitas");
//        let kelDomField = document.querySelector("#kelurahanDomisili");
//        let kodPosDomField = document.querySelector("#kodeposDomisili");
//        let kecDomField = document.querySelector("#kecamatanDomisili");
//        let kabDomField = document.querySelector("#kotakabupatenDomisili");
//        let provDomField = document.querySelector("#provinsiDomisili");
//        let tempatLahirField = document.querySelector("#tempatlahir");
//        let tanggalLahirField = document.querySelector("#tanggallahir");
//        let nomorTelpField = document.querySelector("#nomortelepon");
//        let nomorHPField = document.querySelector("#nomorhandphone");
//
//        identitasField.textContent = ubah.nomorIdentitas;
//        namaField.textContent = ubah.nama;
//        kelaminField.textContent = ubah.jenisKelamin;
//        npwpField.textContent = ubah.npwp;
//        alamatField.textContent = ubah.alamatIdentitas;
//        kelIdenField.textContent = ubah.kelurahanIdentitas;
//        kodPosIdenField.textContent = ubah.kodePosIdentitas;
//        kecIdenField.textContent = ubah.kecamatanIdentitas;
//        kabIdenField.textContent = ubah.kotaIdentitas;
//        provIdenField.textContent = ubah.provinsiIdentitas;
//        alamatDomField.textContent = ubah.alamatDomisili;
//        kelDomField.textContent = ubah.kelurahanDomisili;
//        kodPosDomField.textContent = ubah.kodePosDomisili;
//        kecDomField.textContent = ubah.kecamatanDomisili;
//        kabDomField.textContent = ubah.kotaDomisili;
//        provDomField.textContent = ubah.provinsiDomisili;
//        tempatLahirField.textContent = ubah.tempatLahir;
//        tanggalLahirField.textContent = ubah.tanggalLahir;
//        nomorTelpField.textContent = ubah.nomorTelepon;
//        nomorHPField.textContent = ubah.nomorHandphone;
//    }

    let populateUbah2 = (ubah) => {
        let bankField = document.querySelector("#bank");
        let rekField = document.querySelector("#rek");
        let namaRekField = document.querySelector("#namarekening");

        bankField.textContent = ubah.bank;
        rekField.textContent = ubah.nomorRekening;
        namaRekField.textContent = ubah.namaRekening;
    }








})()