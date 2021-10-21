Rails.application.routes.draw do
  namespace :admin do
    resources :users
    resources :accommodations
    resources :activities
    resources :trips
    resources :addresses
    resources :places

    root to: "users#index"
  end

  resources :accommodations
  resources :activities
  resources :places

  resources :trips do
    resources :accommodations
    resources :activities
    resources :places
  end

  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root "public#index"

  devise_for :users
end
