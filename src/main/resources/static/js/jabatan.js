let urlJabatan = 'http://localhost:8082/api/jabatan/getSearchItems/'; // URL yang mengarah ke controller baru
let searchInputJabatan = document.querySelector('input.searchJabatan');
let suggestionsContainerJabatan = document.querySelector('.search-container-filter .suggestions');
let selectFilterJabatan = document.querySelector('.filter select'); // Elemen select filter
suggestionsContainerJabatan.style.display = 'none';

if (searchInputJabatan) {
    searchInputJabatan.addEventListener('input', function () {
        const query = searchInputJabatan.value.toLowerCase(); // Mengambil input yang dimasukkan oleh pengguna
        const filterValue = selectFilterJabatan.value; // Mengambil nilai filter yang dipilih
        suggestionsContainerJabatan.innerHTML = ''; // Menghapus daftar suggestion sebelumnya

        // Menyembunyikan suggestion jika input kosong
        if (!query) {
            suggestionsContainerJabatan.style.display = 'none';
            return; // Tidak melanjutkan proses pencarian jika input kosong
        }

        // Menampilkan suggestion container jika ada input
        suggestionsContainerJabatan.style.display = 'block';

        // Melakukan request ke API dengan menambahkan filter sebagai bagian dari URL
        fetch(`${urlJabatan}${filterValue}`)
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
                        searchInputJabatan.value = item; // Mengisi input dengan item yang dipilih
                        suggestionsContainerJabatan.innerHTML = ''; // Menghapus suggestion setelah dipilih
                        suggestionsContainerJabatan.style.display = 'none'; // Menyembunyikan suggestion setelah dipilih
                    });

                    suggestionsContainerJabatan.appendChild(div); // Menambahkan suggestion ke container
                });

                // Jika tidak ada hasil yang cocok, menyembunyikan suggestion
                if (filteredData.length === 0) {
                    suggestionsContainerJabatan.innerHTML = '<div class="no-results">No results found</div>';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error); // Menangani error jika request gagal
            });
    });
}
