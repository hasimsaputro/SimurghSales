let urlModel = 'http://localhost:8082/api/model/';
let searchModel = document.querySelector('input#search.searchIndexModel');
let suggestionsModelContainer = document.querySelector('.search-container-filter .suggestions.model');
let selectModelFilter = document.querySelector('.filter.model select');

if(searchModel){
  searchModel.addEventListener('input', function() {
      const query = searchModel.value.toLowerCase();
      const filterValue = selectModelFilter.value;
      suggestionsModelContainer.innerHTML = '';
      fetch(`${urlModel}getSearchItems=${filterValue}`)
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
                              searchModel.value = item;
                              suggestionsModelContainer.innerHTML = '';
                          });

                          suggestionsModelContainer.appendChild(div);
                              });
                          }
                      })
                      .catch(error => {
                          console.error('Error fetching data:', error);
                      });
          });
      }

      let kategoriModelInput = document.querySelector('input#namaKategoriModel.namaKategoriModelInput');
      let idKategoriModel = document.querySelector('input#idKategoriModel.idKategoriModelInput');
      let suggestionsKategoriModel = document.querySelector('.search-container .suggestions.modelKategori');

      if(kategoriModelInput && suggestionsKategoriModel){
          kategoriModelInput.addEventListener('input',()=>{
              const query = kategoriModelInput.value.toLowerCase();
              suggestionsKategoriModel.innerHTML = '';
              fetch(`${urlModel}getKategori`)
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
                                kategoriModelInput.value = item;
                                idKategoriModel.value = data.find(option => option.text === item)?.value;
                                suggestionsKategoriModel.innerHTML = '';
                            });

                            suggestionsKategoriModel.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
      }

    let merkModelInput = document.querySelector('input#namaMerkModel.namaMerkModelInput');
    let idMerkModel = document.querySelector('input#idMerkModel.idMerkModelInput');
    let suggestionsMerkModel = document.querySelector('.search-container .suggestions.modelMerk');

    if(merkModelInput && suggestionsMerkModel){
        merkModelInput.addEventListener('input',()=>{
            const query = merkModelInput.value.toLowerCase();
            suggestionsMerkModel.innerHTML = '';
            fetch(`${urlModel}getMerk`)
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
                              merkModelInput.value = item;
                              idMerkModel.value = data.find(option => option.text === item)?.value;
                              suggestionsMerkModel.innerHTML = '';
                          });

                          suggestionsMerkModel.appendChild(div);
                      });
                  }
              })
              .catch(error => {
                  console.error('Error fetching data:', error);
              });
            });
    }

    let tipeModelInput = document.querySelector('input#namaTipeModel.namaTipeModelInput');
    let idTipeModel = document.querySelector('input#idTipeModel.idTipeModelInput');
    let suggestionsTipeModel = document.querySelector('.search-container .suggestions.modelTipe');

    let jenisModelInput = document.querySelector('input#namaJenisModel.namaJenisModelInput');
    let idJenisModel = document.querySelector('input#idJenisModel.idJenisModelInput');

    if(tipeModelInput && suggestionsTipeModel){
        tipeModelInput.addEventListener('input',()=>{
            const query = tipeModelInput.value.toLowerCase();
            suggestionsTipeModel.innerHTML = '';
            fetch(`${urlModel}getTipe`)
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
                              tipeModelInput.value = item;
                              idTipeModel.value = data.find(option => option.text === item)?.value;
                              const idTipe = data.find(option => option.text === item)?.value;
                              suggestionsTipeModel.innerHTML = '';

                              fetch(`${urlModel}getTipeJenis=${idTipe}`)
                                  .then(response => response.json())
                                  .then(data => {
                                      jenisModelInput.value = data.jenis.text;
                                      idJenisModel.value = data.jenis.value;
                                  })
                                  .catch(error => {
                                      console.error('Error fetching data:', error);
                                  });
                          });

                          suggestionsTipeModel.appendChild(div);
                      });
                  }
              })
              .catch(error => {
                  console.error('Error fetching data:', error);
              });
            });
    }