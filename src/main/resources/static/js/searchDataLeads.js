let urlDataleads = 'http://localhost:8082/api/dataLeads/';
let urlEstimasi = 'http://localhost:8082/api/dataLeads/estimasiNilai';
let searchInput = document.querySelector('input.searchDataLeads');
let suggestionsContainer = document.querySelector('.search-container-filter .suggestions');
let selectFilter = document.querySelector('.filter select');


let potInput = document.querySelector('input#pot.searchFormPOT');
let suggestionPot = document.querySelector('div.suggestionDataLeadsPOT');

if(searchInput){
    searchInput.addEventListener('input', function() {
        const query = searchInput.value.toLowerCase();
        const filterValue = selectFilter.value;
        console.log(filterValue);
        suggestionsContainer.innerHTML = '';
        fetch(`${urlDataleads}getSearchItems=${filterValue}`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    console.log(items)
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        console.log(filteredData)
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                searchInput.value = item;
                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
    });
}

let tenorInput = document.querySelector('input#tenor.tenorInput');
let estimasiInput = document.querySelector('input#estimasi-nilai-funding.nilaiFundingInput');

let kategoriInput = document.querySelector('input#kategori.kategoriInput');

let merkInput = document.querySelector('input#merk.merkInput');
let suggestionMerk = document.querySelector('div.suggestionDataLeadsMerk');

let tipeInput = document.querySelector('input#tipe.tipeInput');
let suggestionTipe = document.querySelector('div.suggestionDataLeadsTipe');

let modelInput = document.querySelector('input#model.modelInput');
let suggestionModel = document.querySelector('div.suggestionDataLeadsModel');

potInput.addEventListener('input',()=>{
    console.log('halooo')
    let potInputValue = potInput.value.toLowerCase();
    suggestionPot.innerHTML = '';
    fetch(`${urlDataleads}getPOT`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    const idPot = data.map(option => option.value);
                    console.log(items)
                    if (potInputValue) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(potInputValue));
                        console.log(filteredData)
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                potInput.value = item;
                                const selectedIdPot = data.find(option => option.text === item)?.value;
                                console.log('Selected idPot:', selectedIdPot);

                                if (selectedIdPot) {
                                    fetch(`${urlDataleads}getPOTData=${selectedIdPot}`)
                                        .then(response => response.json())
                                        .then(data => {
                                            tenorInput.value = data.tenor;
                                            kategoriInput.value = data.kategori.text;
                                            let idMerk;
                                            let idTipe;
                                            let idModel;
                                            if(merkInput){
                                                merkInput.addEventListener('input',()=>{
                                                    let merkInputValue = merkInput.value.toLowerCase();
                                                    suggestionMerk.innerHTML = '';
                                                    const merkData = data.merk;
                                                    if(merkInputValue){
                                                        const filteredMerkData = merkData.filter(item => item.text.toLowerCase().includes(merkInputValue));
                                                        filteredMerkData.forEach(item => {
                                                            const divMerk = document.createElement('div');
                                                            divMerk.classList.add('suggestion-item');
                                                            divMerk.textContent = item.text;
                                                            divMerk.addEventListener('click', function() {
                                                                merkInput.value = item.text;
                                                                idMerk = item.value;
                                                                console.log(idMerk);
                                                                suggestionMerk.innerHTML = '';
                                                            });
                                                            suggestionMerk.appendChild(divMerk);
                                                        })
                                                    }
                                                })
                                            }
                                            if(tipeInput){
                                                tipeInput.addEventListener('input',()=>{
                                                    let tipeInputValue = tipeInput.value.toLowerCase();
                                                    suggestionTipe.innerHTML = '';
                                                    const tipeData = data.tipe;
                                                    if(tipeInputValue){
                                                        const filteredTipeData = tipeData.filter(item => item.text.toLowerCase().includes(tipeInputValue));
                                                        filteredTipeData.forEach(item => {
                                                            const divTipe = document.createElement('div');
                                                            divTipe.classList.add('suggestion-item');
                                                            divTipe.textContent = item.text;
                                                            divTipe.addEventListener('click', function() {
                                                                tipeInput.value = item.text;
                                                                idTipe = item.value;
                                                                suggestionTipe.innerHTML = '';
                                                            });
                                                            suggestionTipe.appendChild(divTipe);
                                                        })
                                                    }
                                                })
                                            }
                                            if(modelInput){
                                                modelInput.addEventListener('input',()=>{
                                                    let modelInputValue = modelInput.value.toLowerCase();
                                                    suggestionModel.innerHTML = '';
                                                    const modelData = data.model;
                                                    if(modelInputValue){
                                                        const filteredModelData = modelData.filter(item => item.text.toLowerCase().includes(modelInputValue));
                                                        filteredModelData.forEach(item => {
                                                            const divModel = document.createElement('div');
                                                            divModel.classList.add('suggestion-item');
                                                            divModel.textContent = item.text;
                                                            divModel.addEventListener('click', function() {
                                                                modelInput.value = item.text;
                                                                const requestData = {
                                                                    idPot: selectedIdPot,
                                                                    idKategori: data.kategori.value,
                                                                    idMerk: idMerk,
                                                                    idTipe: idTipe,
                                                                    idModel: item.value,
                                                                };
                                                                console.log(requestData);
                                                                fetch(urlEstimasi, {
                                                                    method: 'POST',
                                                                    headers: {
                                                                        'Content-Type': 'application/json',
                                                                    },
                                                                    body: JSON.stringify(requestData),})
                                                                    .then(response => {
                                                                        if (!response.ok) {
                                                                            throw new Error('Network response was not ok');
                                                                        }
                                                                        return response.text(); // Mengembalikan respons sebagai string
                                                                    })
                                                                    .then(data => {
                                                                        estimasiInput.value = data; // Tampilkan respons dari server
                                                                    })
                                                                    .catch(error => {
                                                                        console.error('Error:', error);
                                                                    });
                                                                suggestionModel.innerHTML = '';
                                                            });
                                                            suggestionModel.appendChild(divModel);
                                                        })
                                                    }
                                                })
                                            }
                                        })
                                        .catch(error => {
                                            console.error('Error fetching data:', error);
                                        });
                                }
                                suggestionPot.innerHTML = '';
                            });

                            suggestionPot.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
})

potInput.addEventListener('keydown',(event)=>{
    if(event.key == 'Enter'){
        console.log('Halo Kau Enter');
    }
})

document.addEventListener('click',function(event){
    if(!event.target.closest('.search-container')){
        suggestionsContainer.innerHTML = '';
    }
})