let urlKeteranganAplikasi = 'http://localhost:8082/api/keteranganAplikasi/getSearchItems/'; // URL yang mengarah ke controller baru
let searchInputKeteranganAplikasi = document.querySelector('input.searchKeteranganAplikasi');
let suggestionsContainerKeteranganAplikasi = document.querySelector('.search-container-filter .suggestions');
let selectFilterKeteranganAplikasi = document.querySelector('.filter select'); // Elemen select filter
suggestionsContainerKeteranganAplikasi.style.display = 'none';

if (searchInputKeteranganAplikasi) {
    searchInputKeteranganAplikasi.addEventListener('input', function () {
        const query = searchInputKeteranganAplikasi.value.toLowerCase(); // Mengambil input yang dimasukkan oleh pengguna
        const filterValue = selectFilterKeteranganAplikasi.value; // Mengambil nilai filter yang dipilih
        suggestionsContainerKeteranganAplikasi.innerHTML = ''; // Menghapus daftar suggestion sebelumnya

        // Menyembunyikan suggestion jika input kosong
        if (!query) {
            suggestionsContainerKeteranganAplikasi.style.display = 'none';
            return; // Tidak melanjutkan proses pencarian jika input kosong
        }

        // Menampilkan suggestion container jika ada input
        suggestionsContainerKeteranganAplikasi.style.display = 'block';

        // Melakukan request ke API dengan menambahkan filter sebagai bagian dari URL
        fetch(`${urlKeteranganAplikasi}${filterValue}`)
            .then(response => response.json()) // Mengonversi response menjadi format JSON
            .then(data => {
                const items = data.map(option => option.text); // Asumsikan response data memiliki properti 'text'
                // Filter hasil berdasarkan query
                const filteredData = items.filter(item => item.toLowerCase().includes(query));

                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item;

                    // Menambahkan event listener untuk memilih suggestion
                    div.addEventListener('click', function () {
                        searchInputKeteranganAplikasi.value = item; // Mengisi input dengan item yang dipilih
                        suggestionsContainerKeteranganAplikasi.innerHTML = ''; // Menghapus suggestion setelah dipilih
                        suggestionsContainerKeteranganAplikasi.style.display = 'none'; // Menyembunyikan suggestion setelah dipilih
                    });

                    suggestionsContainerKeteranganAplikasi.appendChild(div); // Menambahkan suggestion ke container
                });

                // Jika tidak ada hasil yang cocok, menyembunyikan suggestion
                if (filteredData.length === 0) {
                    suggestionsContainerKeteranganAplikasi.innerHTML = '<div class="no-results">No results found</div>';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error); // Menangani error jika request gagal
            });
    });
}
