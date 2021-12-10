Rails.application.routes.draw do
  resources :true_costs
  namespace :admin do
    resources :users
    resources :accommodations
    resources :activities
    resources :trips
    resources :addresses
    resources :places
    resources :invites

    root to: "users#index"
  end

  resources :accommodations, only: %i[ index show ]
  resources :activities, only: %i[ index show ]
  resources :places, only: %i[ index show create update ]
  get 'invites/pending', to: 'trips/invites#pending'

  resources :trips do
    resources :accommodations, controller: 'trips/accommodations'
    resources :activities, controller: 'trips/activities'
    resources :places, controller: 'trips/places'
    resources :invites, except: :pending, controller: 'trips/invites'
    resources :true_costs, controller: 'trips/true_costs'
  end

  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root "public#index"

  devise_for :users
end
