let searchInput = document.querySelector('input.searchDataLeads');
let suggestionsContainer = document.querySelector('.search-container .suggestions');
let selectFilter = document.querySelector('.filter select');
let urlTipeAplikasi = 'http://localhost:8082/api/tipeAplikasi/';

searchInput.addEventListener('input', function() {
    const query = searchInput.value.toLowerCase();

    // Mendapatkan opsi yang dipilih di dropdown
    const selectedOption = selectFilter.options[selectFilter.selectedIndex];

    // Mendapatkan nilai filter dari option yang dipilih
    const filterValue = selectedOption.value;

    // Mendapatkan nilai dari input hidden yang ada di dalam option
    const hiddenInputValue = selectedOption.querySelector('input[type="hidden"]')?.value;
    console.log('Selected filter value:', filterValue);
    console.log('Hidden input value:', hiddenInputValue);

    // Clear previous suggestions
    suggestionsContainer.innerHTML = '';

    // Cek jika ada query untuk pencarian
    if (query) {
        // Kirim request ke API berdasarkan filter dan pencarian
        fetch(`${urlTipeAplikasi}getSearchItems/${filterValue}`)
            .then(response => response.json())
            .then(data => {
                const items = data.map(option => option.text);
                console.log(items);

                // Filter data berdasarkan query pencarian
                const filteredData = items.filter(item => item.toLowerCase().includes(query));
                console.log(filteredData);

                // Tampilkan saran pencarian
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
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }
});

// Menyembunyikan saran saat klik di luar container pencarian
document.addEventListener('click', function(event) {
    if (!event.target.closest('.search-container')) {
        suggestionsContainer.innerHTML = '';
    }
});
