let urlPot = 'http://localhost:8082/api/pot/';

function addFilter() {
    const container = document.querySelector('div.filter-column');
    const firstFilter = container.querySelector('div.filter-row');
    const filterDataElement = document.querySelector('div.filterData');
    const filterLength = parseInt(filterDataElement.getAttribute('data-length'), 10);

    // Hitung jumlah filter yang sudah ditambahkan
    const currentFilterCount = container.querySelectorAll('.filter-row').length;

    // Batasi jumlah filter-row tidak lebih dari jumlah filterItem
    if (currentFilterCount >= filterLength) {
        alert("Jumlah filter maksimal sudah tercapai.");
        return;
    }

    const newFilterRow = document.createElement('div');
        newFilterRow.classList.add('filter-row');

        // Membuat elemen select
        const newSelect = document.createElement('select');
        newSelect.name = "filter";
        newSelect.setAttribute('onchange', 'updateInputType(this)');

        const defaultOption = document.createElement('option');
        defaultOption.value = 'null';
        defaultOption.textContent = 'Pilih Seller';
        newSelect.appendChild(defaultOption);

        fetch(`${urlPot}getFilterItems`)
                .then(response => response.json())
                .then(data => {
                    const filterItems = data;
                    console.log(filterItems);
                    filterItems.forEach(filter => {
                        const option = document.createElement('option');
                        option.value = filter.value;
                        option.textContent = filter.text;
                        newSelect.appendChild(option);
                    });

                    newFilterRow.appendChild(newSelect);

                    // Membuat elemen input text (atau date)
                    const searchContainer = document.createElement('div');
                    searchContainer.classList.add('search-container');

                    const newInput = document.createElement('input');
                    newInput.type = 'text';  // Default sebagai text
                    newInput.name = `search_${currentFilterCount}`;  // Memberikan nama unik pada setiap input
                    newInput.classList.add('searchPot');  // Memberikan class yang unik
                    newInput.classList.add(`${currentFilterCount}`);  // Memberikan class yang unik

                    const searchIcon = document.createElement('i');
                    searchIcon.classList.add('fas', 'fa-search', 'search-icon');

                    const suggestionsDiv = document.createElement('div');
                    suggestionsDiv.classList.add('suggestions');

                    // Menambahkan elemen-elemen tersebut ke dalam search-container
                    searchContainer.appendChild(newInput);
                    searchContainer.appendChild(searchIcon);
                    searchContainer.appendChild(suggestionsDiv);

                    // Menambahkan search-container ke dalam filter-row
                    newFilterRow.appendChild(searchContainer);

                    // Membuat tombol remove
                    const removeButton = document.createElement('button');
                    removeButton.type = 'button';
                    removeButton.classList.add('remove-filter');
                    removeButton.textContent = '−';
                    removeButton.onclick = function () {
                        removeFilter(this);
                    };
                    newFilterRow.appendChild(removeButton);

                    // Menambahkan filter baru ke dalam container
                    container.appendChild(newFilterRow);
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
//    const newFilter = firstFilter.cloneNode(true);
//
//    newFilter.querySelector('input[name="search"]').value = "";
//
//    // Pastikan tombol remove-filter terlihat hanya pada filter tambahan
//    const removeButton = newFilter.querySelector('.remove-filter');
//    removeButton.hidden = false; // Tampilkan tombol remove-filter untuk filter baru
//    removeButton.onclick = function () {
//        removeFilter(this);
//    };
//    container.appendChild(newFilter);
}

function removeFilter(button) {
    const row = button.parentElement;
    row.remove();
}

function updateInputType(select) {
    const filterRow = select.closest('.filter-row');

    const input = filterRow.querySelector('input.searchPot');
    const icon = filterRow.querySelector('i.search-icon');
    const selectedValue = select.value;


    // Periksa jika nilai yang dipilih mengandung kata 'Tanggal'
    if (selectedValue.includes("tanggal")) {
        input.type = "date";
        if(icon){
            icon.style.display = 'none';
        }
    } else {
        input.type = "text";
        if(icon){
            icon.style.display = 'inline-block';
        }
    }
}