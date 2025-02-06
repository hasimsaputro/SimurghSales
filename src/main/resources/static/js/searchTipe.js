let urlTipe = 'http://localhost:8082/api/tipe/';
let searchTipe = document.querySelector('input#search.searchIndexTipe');
let suggestionsTipeContainer = document.querySelector('.search-container-filter .suggestions.tipe');
let selectTipeFilter = document.querySelector('.filter.tipe select');

if(searchTipe){
  searchTipe.addEventListener('input', function() {
      const query = searchTipe.value.toLowerCase();
      const filterValue = selectTipeFilter.value;
      suggestionsTipeContainer.innerHTML = '';
      fetch(`${urlTipe}getSearchItems=${filterValue}`)
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
                              searchTipe.value = item;
                              suggestionsTipeContainer.innerHTML = '';
                          });

                          suggestionsTipeContainer.appendChild(div);
                              });
                          }
                      })
                      .catch(error => {
                          console.error('Error fetching data:', error);
                      });
          });
      }

      let kategoriTipeInput = document.querySelector('input#namaKategoriTipe.namaKategoriTipeInput');
      let idKategoriTipe = document.querySelector('input#idKategoriTipe.idKategoriTipeInput');
      let suggestionsKategoriTipe = document.querySelector('.search-container .suggestions.tipeKategori');

      if(kategoriTipeInput && suggestionsKategoriTipe){
          kategoriTipeInput.addEventListener('input',()=>{
              const query = kategoriTipeInput.value.toLowerCase();
              suggestionsKategoriTipe.innerHTML = '';
              fetch(`${urlTipe}getKategori`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    const idKategori = data.map(option => option.value);
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                kategoriTipeInput.value = item;
                                idKategoriTipe.value = data.find(option => option.text === item)?.value;
                                suggestionsKategoriTipe.innerHTML = '';
                            });

                            suggestionsKategoriTipe.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
              });
      }

    let merkTipeInput = document.querySelector('input#namaMerkTipe.namaMerkTipeInput');
    let idMerkTipe = document.querySelector('input#idMerkTipe.idMerkTipeInput');
    let suggestionsMerkTipe = document.querySelector('.search-container .suggestions.tipeMerk');

    if(merkTipeInput && suggestionsMerkTipe){
        merkTipeInput.addEventListener('input',()=>{
            const query = merkTipeInput.value.toLowerCase();
            suggestionsMerkTipe.innerHTML = '';
            fetch(`${urlTipe}getMerk`)
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
                              merkTipeInput.value = item;
                              idMerkTipe.value = data.find(option => option.text === item)?.value;
                              suggestionsMerkTipe.innerHTML = '';
                          });

                          suggestionsMerkTipe.appendChild(div);
                      });
                  }
              })
              .catch(error => {
                  console.error('Error fetching data:', error);
              });
            });
    }

    let jenisTipeInput = document.querySelector('input#namaJenisTipe.namaJenisTipeInput');
    let idJenisTipe = document.querySelector('input#idJenisTipe.idJenisTipeInput');
    let suggestionsJenisTipe = document.querySelector('.search-container .suggestions.tipeJenis');

    if(jenisTipeInput && suggestionsJenisTipe){
        jenisTipeInput.addEventListener('input',()=>{
            const query = jenisTipeInput.value.toLowerCase();
            suggestionsJenisTipe.innerHTML = '';
            fetch(`${urlTipe}getJenis`)
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
                              jenisTipeInput.value = item;
                              idJenisTipe.value = data.find(option => option.text === item)?.value;
                              suggestionsJenisTipe.innerHTML = '';
                          });

                          suggestionsJenisTipe.appendChild(div);
                      });
                  }
              })
              .catch(error => {
                  console.error('Error fetching data:', error);
              });
            });
    }